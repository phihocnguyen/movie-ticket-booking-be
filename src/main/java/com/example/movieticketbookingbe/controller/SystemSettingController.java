package com.example.movieticketbookingbe.controller;

import com.example.movieticketbookingbe.dto.ApiResponseDTO;
import com.example.movieticketbookingbe.dto.systemsetting.SystemSettingDTO;
import com.example.movieticketbookingbe.dto.systemsetting.SystemSettingPatchDTO;

import com.example.movieticketbookingbe.mapper.SystemSettingMapper;
import com.example.movieticketbookingbe.service.SystemSettingService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system-setting")
@RequiredArgsConstructor
public class SystemSettingController {

    private final SystemSettingService systemSettingService;

    @Operation(
            summary = "Lấy setting theo owner",
            description = "Trả về thông tin cấu hình hệ thống tương ứng với một theater owner cụ thể dựa vào ownerId"
    )
    @GetMapping("/{ownerId}")
    public ResponseEntity<ApiResponseDTO<SystemSettingDTO>> getSettingByOwner(@PathVariable Long ownerId) {
        SystemSettingDTO dto = SystemSettingMapper.toResponseDTO(
                systemSettingService.getSettingByOwnerId(ownerId)
        );
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy setting thành công",dto ));
    }

    @Operation(
            summary = "Lấy setting toàn hệ thống. Dành cho admin",
            description = "Trả về cấu hình hệ thống chung của toàn bộ hệ thống (global), dành cho admin"
    )
    @GetMapping("/global")
    public ResponseEntity<ApiResponseDTO<SystemSettingDTO>> getGlobalSetting() {

        SystemSettingDTO dto = SystemSettingMapper.toResponseDTO(
                systemSettingService.getGlobalSetting()
        );
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Lấy setting thành công",dto ));

    }
    @Operation(
            summary = "Tạo setting mới.Nếu không truyền ownerId thì sẽ tạo setting cho admin (global), nếu có ownerId thì sẽ tạo cho theater owner",
            description = "Tạo một bản ghi cấu hình hệ thống mới. Nếu không truyền ownerId thì sẽ tạo setting cho admin (global), nếu có ownerId thì sẽ tạo cho theater owner tương ứng."
    )
    @PostMapping("/create")
    public ResponseEntity<ApiResponseDTO<SystemSettingDTO>> createSetting(
            @RequestBody SystemSettingPatchDTO dto,
            @RequestParam(required = false) Long ownerId
    ) {
        SystemSettingDTO result = systemSettingService.create(dto, ownerId);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Tạo setting thành công", result));
    }

    @Operation(
            summary = "Cập nhật setting.Nếu không truyền ownerId thì cập nhật setting cho admin, nếu có ownerId thì cập nhật cho theater",
            description = "Cập nhật cấu hình hệ thống hiện tại. Nếu không truyền ownerId thì cập nhật setting cho admin, nếu có ownerId thì cập nhật cho theater owner tương ứng. Hỗ trợ cập nhật một phần."
    )
    @PatchMapping("/update")
    public ResponseEntity<ApiResponseDTO<SystemSettingDTO>> updateSetting(
            @RequestBody SystemSettingPatchDTO patchDTO,
            @RequestParam(required = false) Long ownerId
    ) {
        SystemSettingDTO dto = systemSettingService.update( ownerId,patchDTO);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Cập nhật thành công", dto));
    }

    @Operation(
            summary = "Xoá setting",
            description = "Xoá cấu hình hệ thống theo ID. Dùng chủ yếu cho mục đích quản trị hoặc dọn dữ liệu test."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteSetting(@PathVariable Long id) {
        systemSettingService.delete(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Đã xoá", null));
    }
}
