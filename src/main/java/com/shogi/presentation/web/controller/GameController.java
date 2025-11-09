package com.shogi.presentation.web.controller;

import com.shogi.application.usecase.Game;
import com.shogi.presentation.web.dto.*;
import com.shogi.presentation.web.mapper.*;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GameController {
  private final Game game = new Game();

  @GetMapping("/health")
  public Map<String, String> health() {
    Map<String, String> health = new HashMap<>();
    health.put("status", "ok");
    return health;
  }

  @GetMapping("/state")
  public Map<String, Object> getState() {
    Map<String, Object> state = new HashMap<>();
    state.put("board", getBoard());
    state.put("stand", getStand());
    state.put("turn", getTurn());
    return state;
  }

  @GetMapping("/board")
  public BoardDto getBoard() {
    return BoardMapper.toDto(game.getBoard());
  }

  @GetMapping("/stand")
  public StandDto getStand() {
    return StandMapper.toDto(game.getStand());
  }

  @GetMapping("/turn")
  public TurnDto getTurn() {
    return TurnMapper.toDto(game.getTurn());
  }
}
