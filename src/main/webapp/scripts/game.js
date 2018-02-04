/**
 * quarto.js
 */
var quarto = {
  property : {
    room : null,
    player : null,
    displayData : null,
    socket : null
  }
};

quarto.enteringRoom = function() {
  var data = {
    roomName : $(".room-modal .room-name").val(),
    player : quarto.property.player
  };

  $.ajax({
    type : "POST",
    url : "./api/play",
    contentType : "application/json",
    dataType : "json",
    data : JSON.stringify(data)
  }).done(function(data) {
    if (!quarto.isValid(data)) {
      return;
    }
    quarto.property.room = data.room;
    quarto.hideEnteringModal();
    quarto.updateScreen();

    if (window.WebSocket) {
      quarto.startSocket();
    }
    window.setInterval(quarto.updateScreen, 5000);
  }).fail(quarto.failedAjax);
};
quarto.startSocket = function() {
  if (window.WebSocket) {
    var url = "ws://" + location.host + location.pathname + "socket/updateScreen";
    var socket = new WebSocket(url);
    socket.onopen = function(event) {
      socket.send(JSON.stringify({
        "room" : quarto.property.room,
        "player" : quarto.property.player
      }));
    };
    socket.onmessage = quarto.updateScreen;
    socket.onclose = function(event) {
      window.setTimeout(quarto.startSocket, 5000);
    };
    quarto.property.socket = socket;
  }
}

quarto.put = function(pieceId, x, y) {
  if (!quarto.allowRequest("PUT")) {
    return;
  }
  var data = {
    "room" : quarto.property.room,
    "player" : quarto.property.player,
    "positionX" : x,
    "positionY" : y
  };
  $.ajax({
    type : "POST",
    url : "./api/put",
    contentType : "application/json",
    dataType : "json",
    data : JSON.stringify(data)
  }).done(function(data) {
    if (!quarto.isValid(data)) {
      return;
    }
    quarto.updateScreen();
  }).fail(quarto.failedAjax);
};

quarto.choose = function(pieceId) {
  if (!quarto.allowRequest("CHOOSE")) {
    return;
  }
  var data = {
    "room" : quarto.property.room,
    "player" : quarto.property.player,
    "chosen" : pieceId
  };
  $.ajax({
    type : "POST",
    url : "./api/choose",
    contentType : "application/json",
    dataType : "json",
    data : JSON.stringify(data)
  }).done(function(data) {
    if (!quarto.isValid(data)) {
      return;
    }
    quarto.updateScreen();
  }).fail(quarto.failedAjax);
};

quarto.allowRequest = function(type) {
  var data = quarto.property.displayData;
  if (data != null) {
    switch (data.gameState) {
    case "CHOOSE_PLAYER_A":
      return data.playerA && type == "CHOOSE";
      break;
    case "CHOOSE_PLAYER_B":
      return data.playerB && type == "CHOOSE";
      break;
    case "PUT_PLAYER_A":
      return data.playerA && type == "PUT";
      break;
    case "PUT_PLAYER_B":
      return data.playerB && type == "PUT";
      break;
    }
  }
  return false;
};

quarto.updateScreen = function() {
  var data = {
    room : quarto.property.room,
    player : quarto.property.player
  };

  $.ajax({
    type : "POST",
    url : "./api/display",
    contentType : "application/json",
    dataType : "json",
    data : JSON.stringify(data)
  }).done(function(data) {
    if (!quarto.isValid(data)) {
      return;
    }
    quarto.property.displayData = data;
    quarto.updateDescription(data);
    quarto.updateStack(data.stack, data.chosen);
    quarto.updateBoard(data.board);
  }).fail(quarto.failedAjax);

};

quarto.updateDescription = function(data) {
  var text = null;

  switch (data.gameState) {
  case "WAITING":
    text = "対戦相手を待っています...";
    break;
  case "DRAW":
    text = "引き分けです...";
    break;
  case "ABORT":
    text = "対戦が中断されました..."
    break;
  case "CHOOSE_PLAYER_A":
    text = data.playerA ? "駒を選んでください..." : "相手が駒を選んでいます...";
    break;
  case "CHOOSE_PLAYER_B":
    text = data.playerB ? "駒を選んでください..." : "相手が駒を選んでいます...";
    break;
  case "PUT_PLAYER_A":
    text = data.playerA ? "駒を配置してください..." : "相手が駒を配置しています...";
    break;
  case "PUT_PLAYER_B":
    text = data.playerB ? "駒を配置してください..." : "相手が駒を配置しています...";
    break;
  case "WIN_PLAYER_A":
    text = data.playerA ? "あなたの勝ちです..." : "あなたの負けです...";
    break;
  case "WIN_PLAYER_B":
    text = data.playerB ? "あなたの勝ちです..." : "あなたの負けです...";
    break;
  }

  $(".description").text(text);
};

quarto.showEnteringModal = function() {
  $(".room-modal .room-name").empty();
  $(".room-modal").modal({
    backdrop : "static",
    keyboard : false,
    show : true
  });
};

quarto.getImageName = function(attributes) {
  var name = "Quarto";

  if ($.inArray("SQUARE", attributes) > -1) {
    name += "Square";
  } else {
    name += "Circle";
  }

  if ($.inArray("TALL", attributes) > -1) {
    name += "High";
  } else {
    name += "Low";
  }

  if ($.inArray("BROWN", attributes) > -1) {
    name += "Blown";
  } else {
    name += "White";
  }

  if ($.inArray("DENT", attributes) > -1) {
    name += "Hole";
  } else {
    name += "Non";
  }

  return "./images/" + name + ".png";
};

quarto.initializeBoard = function() {
  var boxW = 0.1,
    boxH = 0.1,
    marginX = 0.024,
    marginY = 0.057,
    left = 0.265,
    top = 0.22,
    maxX = 4,
    maxY = 4;
  var $parent = $(".board-wrapper");
  for (var x = 0; x < maxX; x++) {
    for (var y = 0; y < maxY; y++) {
      var css = {
        "position" : "absolute",
        "width" : (100 * boxW) + "%",
        "height" : (100 * boxH) + "%",
        "left" : (100 * (left + (boxW + marginX) * x)) + "%",
        "top" : (100 * (top + (boxH + marginY) * y)) + "%"
      };
      var $imgBox = $("<div>").addClass("board-img").css(css).css({
        "z-index" : y
      }).attr({
        "data-pos-x" : x,
        "data-pos-y" : y
      }).addClass("box").appendTo($parent);

      $("<div>").addClass("board-collision").css(css).css({
        "z-index" : y + 10
      }).prop({
        "data-img-box" : $imgBox
      }).on("click", quarto.tapCollisionBox).appendTo($parent);
    }
  }
};

quarto.initializeStack = function() {
  $(".piece-container").on("click", ".piece-wrapper", quarto.tapStackPiece);
};

quarto.tapStackPiece = function(event) {
  var pieceId = $(event.target).parent(".piece-wrapper").attr("data-id");
  quarto.choose(pieceId);
};

quarto.tapCollisionBox = function(event) {
  var $imgBox = $(event.target).prop("data-img-box");
  var chosen = quarto.property.displayData.chosen;
  var x = $imgBox.attr("data-pos-x");
  var y = $imgBox.attr("data-pos-y");
  quarto.put(chosen, x, y);
};

quarto.updateStack = function(stack, chosen) {
  var $parent = $(".piece-container");
  $parent.empty();
  $.each(stack, function(index, piece) {
    var $piece = $(".template .stack-piece").children().clone();
    $piece.attr({
      "data-id" : piece.id
    }).find("img").attr({
      "src" : quarto.getImageName(piece.attributes)
    });
    $piece.appendTo($parent);
    if (chosen == piece.id) {
      $piece.find("img").addClass("strong");
    }
  });
};

quarto.updateBoard = function(board) {
  var array = quarto.boardTo2DArray(board);
  var $parent = $(".board-wrapper");
  $parent.find(".board-img").empty();

  $.each($parent.find(".board-img"), function(index, element) {
    var $element = $(element);
    var x = new Number($element.attr("data-pos-x"));
    var y = new Number($element.attr("data-pos-y"));
    var piece = array[x][y];
    if (piece != null) {
      $("<img>").on("click", function(event) {
        event.stopImmediatePropagation();
      }).css({
        "position" : "absolute",
        "bottom" : 0
      }).attr({
        "src" : quarto.getImageName(piece.attributes)
      }).addClass("img-responsive").addClass("piece").appendTo($element);
    }
  });
};

quarto.hideEnteringModal = function() {
  $(".room-modal").modal("hide");
};

quarto.boardTo2DArray = function(board) {
  var xD = 4;
  var yD = 4;
  var x;
  var y;
  var array = new Array(xD);
  for (x = 0; x < xD; x++) {
    array[x] = new Array(yD);
    for (y = 0; y < yD; y++) {
      array[x][y] = null;
    }
  }
  $.each(board, function(index, element) {
    var p = element.position;
    array[p.x][p.y] = element.piece;
  });
  return array;
}

quarto.isValid = function(data) {
  var result = false;
  var reason = null;
  try {
    if (data.result == "ok") {
      result = true;
    } else if (data.result == "error") {
      reason = data.reason;
    } else {
      throw new Error();
    }
  } catch (e) {
    result = false;
    reason = "unknown";
  }
  if (!result) {
    $(".error-modal .error-message").text(reason);
    $(".error-modal").modal("show");
  }
  return result;
};

quarto.failedAjax = function() {
  var data = {
    "result" : "error",
    "reason" : "ajax"
  };
  quarto.isValid(data);
};

quarto.initialize = function() {
  $(".entering-room").on("click", quarto.enteringRoom);

  quarto.initializeBoard();
  quarto.initializeStack();
  quarto.showEnteringModal();
};

$(quarto.initialize);