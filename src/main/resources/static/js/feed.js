let page = 0;

$(window).scroll(function() {
    let scrollTop = $(window).scrollTop();
    let documentHeight = $(document).height();
    let windowHeight = $(window).height();

    /*버그 수정 (근사치 계산)*/
    let checkNum = ($(window).scrollTop()) - ($(document).height() - $(window).height());
    if (checkNum < 1 && checkNum > -1) {
        page++;
        scroll(); // 박스 3개씩 로드
    }

    let isBottom = documentHeight == scrollTop + windowHeight;
    if (isBottom) {
      $(".loader").show();
      setTimeout(function () {
        $(".loader").hide();
      }, 1500);
    }
});

function appendMainPostList(post) {
    box = `<div class="photo u-default-box">`;
    box += `<header class="photo__header">`;
    box += `<img src="/upload/${post.profileImage}" onerror="this.onerror=null; this.src='/images/profile_default.jpg'"/>`;
    box += `<div class="photo_user_info">`;
    box += `<span class="photo__username">${post.username}</span>`;
    box += `<span class="photo__location">${post.location}</span></div></header>`;
    box += `<div class="photo_post_image">`;
    box += `<img src="/upload/${image.postImage}" /></div>`;
    box += `<div class="photo__info"><div class="photo__actions"><span class="photo__action">`;

    if(post.heart == true){
        box += `<i onclick=“like(${post.id})" id=“${post.id}" class="fa fa-heart heart heart-clicked"></i>`;
    }else{
        box += `<i onclick="like(${post.id})" id=“${post.id}" class="fa fa-heart-o heart"></i>`;
    }

    box += `</span> <span class="photo__action">`;
    box += `<i class="fa fa-comment-o"></i></span></div>`;

    // 수정 좋아요 카운트 증가
    box += `<span class="photo__likes">좋아요 <span class="photo__likes" id="photo_likes_count_${post.id}">${post.likeCount}</span><span class="photo__likes">개</span></span>`;
    box += `<div class="photo_caption"><a class="photo__username" href="#" style="color:black;">${post.username} </a>`;
    box += `<span>${post.content}</span></div><div class="photo_tag">`;

    post.tags.forEach(function(tag) {
        box += `#${tag.name} `;
    });

    box +=`</div>`;
    box += `<ul class="photo__comments">`;

    post.reviews.forEach(function(review) {
        box += `<li class="photo__comment">`;
        box += `<span class="photo__comment-author">${review.user.nickname}</span>`;
        box += `<span>${review.content}</span></li>`
    }

    box += `</ul><span class="photo__date">${post.createDate}</span>`;
    box += `<div class="photo__add-comment-container">`;
    box += `<textarea class="review_content" id="review_content_${post.id}" placeholder="댓글 달기..." name="content"></textarea>`;
    box += `<button class="review_btn" type="button" onclick="prepare(${post.id})" disabled="disabled" >게시</button></div></div></div >`;

    return box;
}

async function scroll(){
    let response = await fetch(`/post/main/scroll?page=${page}`);
    let posts = await response.json();

    posts.forEach(function(post){
        let tag = appendMainPostList(post);
        $("#feed").append(tag);
    });
}
















