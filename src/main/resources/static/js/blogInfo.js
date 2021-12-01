//获取当前blog的id   bid
var url = document.location.toString();
arrUrl = url.split("/");
bidString = arrUrl[arrUrl.length-1];
var bid = parseInt(bidString);

//flag=1 当前用户已点赞 flag=0 未点赞
var likeFlag = 0;

$(function () {
    //判断是否已点赞
    judgeLikeed();
    // console.log(likeFlag)
    // if (likeFlag==1){
    //     console.log("已点赞")
    //     document.getElementById("like").style.color="#f26682";
    // }

    //点赞
    $("#like").click(function () {

        $.ajax({
            type:"GET",
            url:"/judgeLogin",
            data:{

            },
            success:function (resp) {
                if (resp==false){
                    var r = confirm("您未登录，点击确认进行登录");
                    if (r==true){
                        window.location.href="http://localhost:8888/tologin";
                    }
                }else if (resp==true){

                    $.ajax({
                        type:"GET",
                        url:"/blog/like",
                        data:{
                            "bid":bid
                        },
                        success:function (resp) {
                            console.log(resp);
                            if (resp==true){
                                alert("点赞成功")
                                location.reload();
                            }
                            else{
                                alert("您已点赞")
                            }
                        }
                    })
                }
            }
        })



    })

    //评论
    $("#comment").click(function () {

        $.ajax({
            type: "GET",
            url: "/judgeLogin",
            data: {},
            success:function (resp) {
                if (resp==false){
                    var r = confirm("您未登录，点击确认进行登录");
                    if (r==true){
                        window.location.href="http://localhost:8888/tologin";
                    }
                }else if (resp=true){
                    var commentContent = prompt("请输入您的评论");
                    if(commentContent == null || commentContent == ""){

                    }else {
                        $.ajax({
                            type: "GET",
                            url: "/blog/comment",
                            data: {
                                "content": commentContent,
                                "bid": bid
                            },
                            success: function (resp) {
                                console.log(resp)
                                if (resp == true) {
                                    alert("评论成功")
                                    location.reload();
                                }
                            }
                        })
                    }
                }
            }
        })



    })

    //评论区
    datehtml="";
    $.ajax({
        type:"GET",
        url:"/blog/showComments",
        data:{
            "bid":bid
        },
        success:function (resp) {
            console.log(resp)
            var i;
            for(i=0;i<resp.length;i++){
                datehtml=datehtml+"<div class=\"panel-body otherHeadPic\">\n" +
                    "            <div class=\"media\">\n" +
                    "                <div class=\"media-left media-middle\">\n" +
                    "                    <a href=\"#\">\n" +
                    "                        <img class=\"media-object img-circle\" src=\""+resp[i].pic+"\" alt=\"...\">\n" +
                    "                    </a>\n" +
                    "                </div>\n" +
                    "                <div class=\"media-body\">\n <br>" +
                    "                    <h4 class=\"media-heading\">"+resp[i].content+"</h4>\n" +
                    "                    </a>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "        </div>"
            }
            $("#commentArea").html(datehtml);
        }
    })


})

//判断是否已点赞
function judgeLikeed() {

    $.ajax({
        type: "GET",
        url:"/blog/judgeUserLiked",
        data: {
            "bid":bid
        },
        success:function (resp) {
            console.log(resp);
            if(resp==true){
                likeFlag=1;
                document.getElementById("like").style.color="#f26682";
            }
            console.log(likeFlag)

        }
    })

}