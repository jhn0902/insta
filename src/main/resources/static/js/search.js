
function search() {
    $(".search_dialog").hide();
    $(".search_dialog").empty();
    let word = $('.search_input').val();
    let url;
    let searchCondition;
    let searchKeyword;
    let tag;

    if (word.search("#") == 0){
        searchCondition = "tag";
        searchKeyword = word.substring(1);
        url = "/search/tags?name=" + word.substring(1);
        name = word.substring(1);
    } else {
        searchCondition = "user";
        searchKeyword = word;
        url = "/search/users?name=" + word;
    }
    if (searchKeyword.trim() != "") {
        $.ajax({
            type: "get",
            url: url,
            success: function(lists) {
                if (lists.length === 0) {
                    $(".search_dialog").hide();
                } else {
                    $(".search_dialog").show();
                }
                $(".search_dialog").empty();

                lists.forEach(function(list){
                    if (searchCondition === "user") {
                        tag = appendUsers(list);
                    } else if (searchCondition === "tag") {
                        tag = appendTags(list);
                    }
                    $(".search_dialog").append(tag);
                });
            }
        });
    }

    $(document).on("click", function() {
        $(".search_dialog").hide();
    });
}

function append(list, searchCondition) {
    let box = `<div class="search_body"><a class="search_link" href="#">`
    box += `<div class="search_img_body">`
    if (searchCondition === "user") {
        if (list.profileImage === null) {
            box += `<img class="search_img" src="/images/profile_default.jpg"/>`
        } else {
            box += `<img class="search_img" src="/upload${list.profileImage}"/>`
        }
        box += `</div><div class="search_info">`
        box += `<div class="info1">${list.nickname}</div>`
        box += `<div class="info2">${list.name}</div></div></a></div>`
    } else {
        box += `<img class="search_img" src="/images/hashtag.png" />`
        box += `</div><div class="search_info">`
        box += `<div class="info1">${list.tagName}</div>`
        box += `<div class="info2">${list.postCount}</div></div></a></div>`
    }
    return box;
}

function appendUsers(list) {
    let box = `<div class="search_body"><a class="search_link" href="/post/userPage?userId=${list.userId}">`
    box += `<div class="search_img_body">`
    if (list.profileImage === null) {
        box += `<img class="search_img" src="/images/profile_default.jpg"/>`
    } else {
        box += `<img class="search_img" src="/upload/${list.profileImage}"/>`
    }
    box += `</div><div class="search_info">`
    box += `<div class="info1">${list.nickname}</div>`
    box += `<div class="info2">${list.name}</div></div></a></div>`

    return box;
}

function appendTags(list) {
    let box = `<div class="search_body"><a class="search_link" href="/post/tag?tagName=${list.tagName}">`
    box += `<div class="search_img_body">`
    box += `<img class="search_img" src="/images/hashtag.png" />`
    box += `</div><div class="search_info">`
    box += `<div class="info1">${list.tagName}</div>`
    box += `<div class="info2">${list.postCount}</div></div></a></div>`

    return box;
}

function toggleDialog() {
    $(".profile_dialog").toggle();
}

