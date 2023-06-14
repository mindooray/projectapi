package com.nhnacademy.team4.projectapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.team4.projectapi.dto.tag.TagDTO;
import com.nhnacademy.team4.projectapi.dto.tag.TagPostDTO;
import com.nhnacademy.team4.projectapi.entity.Tag;
import com.nhnacademy.team4.projectapi.service.TagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TagController.class)
class TagControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    TagService tagService;
    @Test
    void createTag() throws Exception{
        // Mock 데이터 설정
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("Tag Name");

        // Mock 객체 및 응답 값 설정
        Tag createdTag = new Tag();
        createdTag.setName(tagDTO.getName());

        given(tagService.createProjectTag(anyLong(), any(TagPostDTO.class))).willReturn(createdTag);

        // 테스트 수행
        mockMvc.perform(post("/projects/{projectId}/tags", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tagDTO)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void updateTag() throws Exception {
        // Mock 데이터 설정
        Long tagId = 1L;
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("Updated Tag Name");

        // Mock 객체 및 응답 값 설정
        Tag updatedTag = new Tag();
        updatedTag.setTagId(tagId);
        updatedTag.setName(tagDTO.getName());

        given(tagService.updateTag(anyLong(), any(TagPostDTO.class))).willReturn(updatedTag);

        // 테스트 수행
        mockMvc.perform(put("/tags/{tagId}", tagId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tagDTO)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.projectId").value(updatedTag.getTagId()))
                .andExpect(jsonPath("$.name").value(updatedTag.getName()));
    }

    @Test
    void deleteTag() throws Exception {
        // Mock 데이터 설정
        Long tagId = 1L;

        // 테스트 수행
        mockMvc.perform(delete("/tags/{tagId}", tagId))
                .andExpect(status().isOk())
                .andDo(print());
    }

}