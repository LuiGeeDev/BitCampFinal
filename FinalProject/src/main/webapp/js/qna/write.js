      console.log("Hello, world!");
      
      $(document).ready(function() {
        const title = $("#input-title");
        const url = $("#input-url");
        const submitBtn = $("#submit-button");
        
        $('[data-toggle="tooltip"]').tooltip();
        
        $("#summernote").summernote({
          height: 485,
          disableResizeEditor: true
        });
        
        if (title.val() == "") {
          submitBtn.attr("disabled", true);
        }
        
        url.keyup(function() {
          if (title.val() == ""
              || url.val() == ""
              || !url.val().startsWith("https://youtu.be/")) {
            console.log("아무것도없쪼?");
            $("#submit-button").attr("disabled", true);
          } else {
            $("#submit-button").attr("disabled", false);
          }
        });
      });