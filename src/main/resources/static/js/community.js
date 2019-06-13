/**
 * 提交回复
 */

function post() {
    var questionId = $('#question_id').val();
    var content = $('#comment_content').val();
    comment2target(questionId, 1, content);
}

function comment(e) {
    var targetId = $(e).data("id");
    var content = $('#input-'+targetId).val();
    comment2target(targetId, 2, content);
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("回复内容不能为空");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                location.reload();
            } else if (response.code == 2003) {
                var isAccepted = confirm(response.message);
                if (isAccepted) {
                    window.open('https://github.com/login/oauth/authorize?client_id=9b955915ead557571674&redirect_uri=http://localhost:8887/callback&scope=user&state=1');
                    window.localStorage.setItem("closable", true);
                }
            } else {
                alert(response.message);
            }
        },
        dataType: "json"
    });
}


/**
 * 展开二级评论
 */

function collapseComments(e) {
    var id = $(e).data("id");
    var collapsed = $(e).attr("collapse");

    if (collapsed) {
        // 折叠二级评论
        $('#comment-'+id).removeClass("in");
        $(e).removeAttr("collapse");
        $(e).removeClass("blue");
    } else {
        //展开二级评论

        $.getJSON("/comment/"+id, function (data) {
           // var commentBody = $('comment-body-'+id);
           // var items = [];
           //
           //
           // $.each(data.data, function (comment) {
           //     $("<div/>", {
           //         "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 collapse sub-comments",
           //         "html": items.join("")
           //     }).appendTo(commentBody);
           // });
           //
           //  $("<div/>", {
           //      "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 collapse sub-comments",
           //      "id": 'comment-' + id,
           //      "html": items.join("")
           //  }).appendTo(commentBody);

            $('#comment-'+id).addClass("in");
            $(e).attr("collapse", "in");
            $(e).addClass("blue");
        });


    }
}