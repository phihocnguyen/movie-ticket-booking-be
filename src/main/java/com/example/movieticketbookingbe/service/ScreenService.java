package com.example.movieticketbookingbe.service;

import com.example.movieticketbookingbe.model.Screen;
import java.util.List;
import java.util.Optional;

public interface ScreenService {
    Screen createScreen(Screen screen);

    Screen updateScreen(Long id, Screen screen);

    void deleteScreen(Long id);

    Optional<Screen> getScreenById(Long id);

    List<Screen> getAllScreens();

    List<Screen> getScreensByTheater(Long theaterId);

    List<Screen> getActiveScreens();

    Optional<Screen> getScreenByNameAndTheater(String screenName, Long theaterId);

    boolean existsByScreenNameAndTheater(String screenName, Long theaterId);

    List<Screen> searchScreens(String screenName, Long theaterId);

    boolean existsByScreenName(String screenName);

    Screen patchScreen(Long id, com.example.movieticketbookingbe.dto.screen.ScreenPatchDTO patchDTO);
}