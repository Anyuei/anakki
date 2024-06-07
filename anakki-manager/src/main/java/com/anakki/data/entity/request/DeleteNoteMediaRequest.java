package com.anakki.data.entity.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName: CreateNoteRequest
 * Description:
 *
 * @author Anakki
 * @date 2024/6/6 3:39
 */
@Data
public class DeleteNoteMediaRequest {
    private String url;
}
