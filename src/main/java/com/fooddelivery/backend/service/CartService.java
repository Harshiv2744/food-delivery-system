package com.fooddelivery.backend.service;

import com.fooddelivery.backend.dto.CartItemRequest;
import com.fooddelivery.backend.dto.CartItemResponse;
import com.fooddelivery.backend.dto.CartResponse;
import com.fooddelivery.backend.exception.ResourceNotFoundException;
import com.fooddelivery.backend.model.Cart;
import com.fooddelivery.backend.model.CartItem;
import com.fooddelivery.backend.model.MenuItem;
import com.fooddelivery.backend.model.User;
import com.fooddelivery.backend.repository.CartRepository;
import com.fooddelivery.backend.repository.MenuItemRepository;
import com.fooddelivery.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;

    @Transactional
    public CartResponse addItem(String userEmail, CartItemRequest request) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart c = Cart.builder().user(user).build();
                    return cartRepository.save(c);
                });
        MenuItem menuItem = menuItemRepository.findById(request.getMenuItemId())
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id " + request.getMenuItemId()));

        CartItem existing = cart.getItems().stream()
                .filter(ci -> ci.getMenuItem().getId().equals(request.getMenuItemId()))
                .findFirst()
                .orElse(null);
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + request.getQuantity());
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .menuItem(menuItem)
                    .quantity(request.getQuantity())
                    .build();
            cart.getItems().add(newItem);
        }
        cartRepository.save(cart);
        return getCart(userEmail);
    }

    @Transactional
    public CartResponse updateItemQuantity(String userEmail, Long cartItemId, int quantity) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        CartItem item = cart.getItems().stream()
                .filter(ci -> ci.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        if (quantity <= 0) {
            cart.getItems().remove(item);
        } else {
            item.setQuantity(quantity);
        }
        cartRepository.save(cart);
        return getCart(userEmail);
    }

    @Transactional
    public CartResponse removeItem(String userEmail, Long cartItemId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        boolean removed = cart.getItems().removeIf(ci -> ci.getId().equals(cartItemId));
        if (!removed) throw new ResourceNotFoundException("Cart item not found");
        cartRepository.save(cart);
        return getCart(userEmail);
    }

    public CartResponse getCart(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user).orElse(null);
        if (cart == null || cart.getItems().isEmpty()) {
            return CartResponse.builder()
                    .id(cart != null ? cart.getId() : null)
                    .items(new ArrayList<>())
                    .totalAmount(0.0)
                    .build();
        }
        List<CartItemResponse> items = cart.getItems().stream()
                .map(ci -> CartItemResponse.builder()
                        .id(ci.getId())
                        .menuItemId(ci.getMenuItem().getId())
                        .menuItemName(ci.getMenuItem().getName())
                        .price(ci.getMenuItem().getPrice())
                        .quantity(ci.getQuantity())
                        .subtotal(ci.getMenuItem().getPrice() * ci.getQuantity())
                        .build())
                .collect(Collectors.toList());
        double total = items.stream().mapToDouble(CartItemResponse::getSubtotal).sum();
        return CartResponse.builder()
                .id(cart.getId())
                .items(items)
                .totalAmount(total)
                .build();
    }
}
