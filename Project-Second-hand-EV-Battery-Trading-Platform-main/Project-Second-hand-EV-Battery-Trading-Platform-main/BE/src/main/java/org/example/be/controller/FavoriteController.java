package org.example.be.controller;

import org.example.be.dto.reponse.ApiResponse;
import org.example.be.dto.request.FavoriteRequest;
import org.example.be.entity.Favorite;
import org.example.be.entity.Member;
import org.example.be.entity.Post;
import org.example.be.service.FavoriteService;
import org.example.be.service.MemberService;
import org.example.be.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PostService postService;

    // ------------------- CREATE -------------------
    @PostMapping
    public ResponseEntity<ApiResponse<Favorite>> createFavorite(@RequestBody FavoriteRequest request) {
        ApiResponse<Favorite> response = new ApiResponse<>();
        try {
            Favorite favorite = new Favorite();
            favorite.setMember(memberService.getMemberById(request.getMemberId()));
            favorite.setPost(postService.getPostById(request.getPostId()).orElse(null));

            Favorite created = favoriteService.createFavorite(favorite);

            Map<String, Object> metadata = new HashMap<>();
            metadata.put("timestamp", LocalDateTime.now());

            response.ok(created, (HashMap<String, Object>) metadata);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());

            response.error(error);
            return ResponseEntity.badRequest().body(response);
        }
    }

    // ------------------- GET ALL -------------------
    @GetMapping
    public ResponseEntity<ApiResponse<List<Favorite>>> getAllFavorites() {
        ApiResponse<List<Favorite>> response = new ApiResponse<>();
        try {
            List<Favorite> favorites = favoriteService.getAllFavorites();

            Map<String, Object> metadata = new HashMap<>();
            metadata.put("count", favorites.size());
            metadata.put("timestamp", LocalDateTime.now());

            response.ok(favorites, (HashMap<String, Object>) metadata);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());

            response.error(error);
            return ResponseEntity.badRequest().body(response);
        }
    }

    // ------------------- GET BY ID -------------------
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Favorite>> getFavoriteById(@PathVariable Integer id) {
        ApiResponse<Favorite> response = new ApiResponse<>();
        try {
            Favorite favorite = favoriteService.getFavoriteById(id);

            Map<String, Object> metadata = new HashMap<>();
            metadata.put("timestamp", LocalDateTime.now());

            response.ok(favorite, (HashMap<String, Object>) metadata);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());

            response.error(error);
            return ResponseEntity.badRequest().body(response);
        }
    }


    // ------------------- UPDATE -------------------
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Favorite>> updateFavorite(@PathVariable Integer id, @RequestBody Favorite favorite) {
        ApiResponse<Favorite> response = new ApiResponse<>();
        try {
            Favorite updated = favoriteService.updateFavorite(id, favorite);

            Map<String, Object> metadata = new HashMap<>();
            metadata.put("updatedAt", LocalDateTime.now());

            response.ok(updated, (HashMap<String, Object>) metadata);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());

            response.error(error);
            return ResponseEntity.badRequest().body(response);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteFavorite(@PathVariable Integer id) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        try {
            favoriteService.deleteFavorite(id);

            response.ok(true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());

            response.error(error);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/latest")
    public ResponseEntity<ApiResponse<List<Post>>> getLatestFavorites(@RequestBody Member member) {
        ApiResponse<List<Post>> response = new ApiResponse<>();
        try {
            List<Post> latestFavorites = favoriteService.getLatestFavoritesPosts(member);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());

            response.error(error);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/first/{memberId}")
    public ResponseEntity<ApiResponse<Favorite>> getFirstFavoriteByMemberId(@PathVariable int memberId) {
        ApiResponse<Favorite> response = new ApiResponse<>();
        try {
            Favorite favorite = favoriteService.getFistFavoriteByMemberID(memberId);
            response.ok(favorite);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            response.error(error);
            return ResponseEntity.badRequest().body(response);

        }
    }
    @GetMapping("/all/{memberId}")
    public ResponseEntity<ApiResponse<List<Favorite>>> getAllFavoriteByMemberId(@PathVariable int memberId) {
        ApiResponse<List<Favorite>> response = new ApiResponse<>();
        try {
            List<Favorite> favorites = favoriteService.getAllFavoriteByMemberID(memberId);
            response.ok(favorites);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            response.error(error);
            return ResponseEntity.badRequest().body(response);

        }
    }

}
