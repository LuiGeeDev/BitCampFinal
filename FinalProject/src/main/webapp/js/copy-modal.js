$(".spinner-border").hide();
$("#search").hide();

$(".modal").on("hidden.bs.modal", function() {
  $("div").remove(".copy-article");
  $(
    ".copy-article-preview-title, .copy-article-preview-writer, .copy-article-preview-content"
  ).empty();
  $("#article_id").val("");
});

function callNextArticles(id, board_id) {
  let lastId = id;

  function changeLastId(new_id) {
    lastId = new_id;
  }

  return function() {
    $.ajax({
      url: "/copy/more",
      type: "post",
      data: { id: lastId, board_id: board_id },
      beforeSend: function() {
        $("#spinner1").show();
      },
      success: function(data) {
        if (data[0]) {
          $(data).each(function(index, article) {
            const time = article.timeLocal;
            const timeStr = `${time.year.toString().slice(2)}-${
              time.monthValue >= 10 ? time.monthValue : "0" + time.monthValue
            }-${
              time.dayOfMonth >= 10 ? time.dayOfMonth : "0" + time.dayOfMonth
            }`;
            $(".copy-article-list .ss-content").append(
              `<div class="copy-article" id="a${
                article.id
              }"><span class="copy-article-title">${
                article.title
              }</span><span class="copy-writer">${
                article.writer.name
              }</span><span class="copy-time">${timeStr}</span></div>`
            );
          });

          changeLastId(data[data.length - 1].id);
        }
      }
    }).done(function() {
      $(".copy-article").off("click");
      addEventToArticle();
      $("#spinner1").hide();
    });
  };
}

function wait(ajax) {
  let timer;
  $("#search").keyup(function() {
    $("div").remove(".copy-article, .copy-no-article");
    if (timer) {
      clearTimeout(timer);
    }
    timer = setTimeout(function() {
      timer = null;
      ajax();
    }, 400);
  });
}

const ajax = function() {
  $.ajax({
    url: "/copy/search",
    method: "post",
    data: {
      title: $("#search").val(),
      board_id: $(".active-board")
        .prop("id")
        .slice(1)
    },
    beforeSend: function() {
      $(
        ".copy-article-preview-title, .copy-article-preview-writer, .copy-article-preview-content"
      ).empty();
      $("#article_id").val("");
      $("#spinner1").show();
    },
    success: function(data) {
      const list = $(".copy-article-list .ss-content");
      if (data.length === 0) {
        list.append(`<div class="copy-no-article">글이 없습니다</div>`);
        return;
      }

      $(data).each(function(index, article) {
        const time = article.timeLocal;
        const timeStr = `${time.year.toString().slice(2)}-${
          time.monthValue >= 10 ? time.monthValue : "0" + time.monthValue
        }-${time.dayOfMonth >= 10 ? time.dayOfMonth : "0" + time.dayOfMonth}`;
        list.append(
          `<div class="copy-article" id="a${
            article.id
          }"><span class="copy-article-title">${
            article.title
          }</span><span class="copy-writer">${
            article.writer.name
          }</span><span class="copy-time">${timeStr}</span></div>`
        );
      });
    }
  }).done(function() {
    addEventToArticle();
    $("#spinner1").hide();
    $(".copy-article-list .ss-content").off("scroll");
  });
};

$(".copy-board").click(function() {
  wait(ajax);
  $(".copy-board").removeClass("active-board");
  $(this).addClass("active-board");
  const id = this.id;
  $.ajax({
    url: "/copy/articles",
    type: "post",
    data: { id: id.slice(1) },
    beforeSend: function() {
      $("div").remove(".copy-article, .copy-no-article");
      $(
        ".copy-article-preview-title, .copy-article-preview-writer, .copy-article-preview-content"
      ).empty();
      $("#spinner1").show();
    },
    success: function(data) {
      const list = $(".copy-article-list .ss-content");
      if (data.length === 0) {
        list.append(`<div class="copy-no-article">글이 없습니다</div>`);
        return;
      }

      $("#search").val("");
      $("#search").show();

      $(data).each(function(index, article) {
        const time = article.timeLocal;
        const timeStr = `${time.year.toString().slice(2)}-${
          time.monthValue >= 10 ? time.monthValue : "0" + time.monthValue
        }-${time.dayOfMonth >= 10 ? time.dayOfMonth : "0" + time.dayOfMonth}`;
        list.append(
          `<div class="copy-article" id="a${
            article.id
          }"><span class="copy-article-title">${
            article.title
          }</span><span class="copy-writer">${
            article.writer.name
          }</span><span class="copy-time">${timeStr}</span></div>`
        );
      });

      const callByScroll = callNextArticles(
        data[data.length - 1].id,
        id.slice(1)
      );
      list.scroll(function() {
        if (list.height() + list.scrollTop() + 0.25 > list[0].scrollHeight) {
          callByScroll();
        }
      });
    }
  }).done(function() {
    $(".copy-article").off("click");
    addEventToArticle();
    $("#spinner1").hide();
  });
});

function addEventToArticle() {
  $(".copy-article").click(function() {
    $(".copy-article").removeClass("active-article");
    $(this).addClass("active-article");
    $.ajax({
      url: "/copy/article",
      type: "post",
      data: { id: this.id.slice(1) },
      beforeSend: function() {
        $(
          ".copy-article-preview-title, .copy-article-preview-writer, .copy-article-preview-content"
        ).empty();
        $("#spinner2").show();
      },
      success: function(article) {
        $(".copy-article-preview-title").text(article.title);
        $(".copy-article-preview-writer").text(article.writer.name);
        $(".copy-article-preview-content").html(article.content);
        $("#article_id").val($("#article_id").val() + article.id + ",");
        const link = $("#copy").prop("href");
        $("#copy").prop(
          "href",
          link.slice(0, link.lastIndexOf("=") + 1) +
            $("#article_id")
              .val()
              .slice(0, -1)
        );
        $(".article-id-list .ss-content").append(
          `<div class="article-id-item"><span class="article-id-title">${
            article.title
          }</span><button class="article-id-delete" id="${
            article.id
          }"><i class="far fa-times-circle"></i></button></div>`
        );

        $(".article-id-delete").off("click");
        $(".article-id-delete").click(function() {
          $(this)
            .parent()
            .remove();
          const oldVal = $("#article_id").val();
          const index = oldVal.indexOf(this.id);
          const newVal =
            oldVal.slice(0, index) +
            oldVal.slice(index + this.id.toString().length + 1);
          $("#article_id").val(newVal);
          const link = $("#copy").prop("href");
          $("#copy").prop(
            "href",
            link.slice(0, link.lastIndexOf("=") + 1) + newVal.slice(0, -1)
          );
        });
      }
    }).done(function() {
      $("#spinner2").hide();
    });
  });
}
