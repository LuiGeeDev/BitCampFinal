calendar.setOption("selectable", true);
calendar.setOption("editable", true);

calendar.on("select", function(info) {
  $("#new-title").val("");
  $("#new-start").val(info.startStr);
  const endDate = new Date(info.end);
  endDate.setDate(endDate.getDate() - 1);
  $("#new-end-display").val(formatDate(endDate));
  $("#new-end").val(info.endStr);
  $("#new-schedule-modal").modal("show");
});

calendar.on("eventClick", function(info) {
  if (info.el.classList.contains("disabled")) {
    return;
  }
  
  const event = info.event;

  $("#title").val(event.title);
  $("#start").val(formatDate(event.start));
  if (event.end) {
    const endDate = info.event.end;
    endDate.setDate(endDate.getDate() - 1);
    $("#end-display").val(formatDate(endDate));
    $("#end").val(formatDate(event.end));
  } else {
    $("#end-display").val(formatDate(event.start));
    $("#end").val(formatDate(event.start));
  }

  $("#id").val(event.id);

  const colors = $("#schedule .calendar-color");
  colors.children(".color").removeClass("active-color");
  switch (event.backgroundColor) {
    case "rgb(255, 59, 48)":
      colors.children(".red").addClass("active-color");
      break;
    case "rgb(255, 149, 0)":
      colors.children(".orange").addClass("active-color");
      break;
    case "rgb(255, 111, 97)":
      colors.children(".coral").addClass("active-color");
      break;
    case "rgb(15, 127, 0)":
      colors.children(".green").addClass("active-color");
      break;
    case "rgb(90, 200, 250)":
      colors.children(".blue").addClass("active-color");
      break;
    case "rgb(5, 121, 255)":
      colors.children(".indigo").addClass("active-color");
      break;
    case "rgb(88, 86, 214)":
      colors.children(".purple").addClass("active-color");
      break;
  }

  $("input[name='color']").val(event.backgroundColor);

  $("#schedule-modal").modal("show");
});

calendar.on("eventDrop", function(info) {
  const event = info.event;
  $.ajax({
    url: "/myclass/schedule/change-date",
    method: "post",
    data: {
      id: event.id,
      start: formatDate(event.start),
      end: formatDate(event.end)
    }
  }).done(function() {
    calendar.refetchEvents();
  });
});

calendar.on("eventResize", function(info) {
  const event = info.event;
  $.ajax({
    url: "/myclass/schedule/change-date",
    method: "post",
    data: {
      id: event.id,
      start: formatDate(event.start),
      end: formatDate(event.end)
    }
  }).done(function() {
    calendar.refetchEvents();
  });
});

$(".color").click(function(event) {
  $(".color").removeClass("active-color");
  $(event.target).addClass("active-color");
  $("input[name='color']").val($(event.target).css("backgroundColor"));
});

$("#submit-new-schedule").click(function() {
  const formData = new FormData($("#new-schedule")[0]);
  $.ajax({
    url: "/myclass/schedule/insert",
    method: "post",
    data: formData,
    contentType: false,
    processData: false
  }).done(function() {
    calendar.refetchEvents();
    $("#new-schedule-modal").modal("hide");
  });
});

$("#submit-schedule").click(function() {
  const formData = new FormData($("#schedule")[0]);
  $.ajax({
    url: "/myclass/schedule/update",
    method: "post",
    data: formData,
    contentType: false,
    processData: false
  }).done(function() {
    calendar.refetchEvents();
    $("#schedule-modal").modal("hide");
  });
});

$("#delete").click(function() {
  $.ajax({
    url: "/myclass/schedule/delete",
    data: { id: $("#id").val() },
    method: "post"
  }).done(function() {
    calendar.refetchEvents();
    $("#schedule-modal").modal("hide");
  });
});

$("#new-schedule, #schedule").submit(function(event) {
  event.preventDefault();
});
