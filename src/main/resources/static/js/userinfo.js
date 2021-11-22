var user = null;
$(function () {
    //获取用户信息
    $.ajax({
        type:"GET",
        url:"/findUser",
        success:function (resp) {
            console.log(resp)
            document.getElementById("username").value=resp.username;
            document.getElementById("email").value=resp.email;
            document.getElementById("phone").value=resp.phone;
            document.getElementById("info").value=resp.info;
            if(resp.sex=="男"){
                $("#sex-men").attr("checked","checked");
            }else{
                $("#sex-women").attr("checked","checked");
            }
            $("#headpic").attr("src",resp.pic)
            user=resp;
        }
    })

    document.getElementById("save").style.display="none";
    document.getElementById("cancle").style.display="none";
    //编辑
    $("#edit").click(function () {
        document.getElementById("username").readOnly=false;
        document.getElementById("phone").readOnly=false;
        document.getElementById("info").readOnly=false;
        document.getElementById("sex-men").disabled=false;
        document.getElementById("sex-women").disabled=false;

        document.getElementById("save").style.display="inline";
        document.getElementById("cancle").style.display="inline";
        document.getElementById("edit").style.display="none";
    })

    //取消编辑
    $("#cancle").click(function () {
        document.getElementById("username").readOnly=true;
        document.getElementById("username").value=user.username;
        document.getElementById("phone").readOnly=true;
        document.getElementById("phone").value=user.phone;
        document.getElementById("info").readOnly=true;
        document.getElementById("info").value=user.info;
        $("#sex-men").attr("checked",false);
        $("#sex-women").attr("checked",false);
        if(user.sex=="男"){
            $("#sex-men").attr("checked","checked");
        }else{
            $("#sex-women").attr("checked","checked");
        }
        document.getElementById("sex-men").disabled=true;
        document.getElementById("sex-women").disabled=true;

        document.getElementById("save").style.display="none";
        document.getElementById("cancle").style.display="none";
        document.getElementById("edit").style.display="inline";
    })

    //保存编辑
    $("#save").click(function () {

        var username = document.getElementById("username").value;
        var email = document.getElementById("email").value;
        var phone = document.getElementById("phone").value;
        var info = document.getElementById("info").value;
        var sex = $('#sex input:radio:checked').val();

        $.ajax({
            type:"GET",
            url:"/updateUser",
            data:{
                "username":username,
                "email":email,
                "phone":phone,
                "info":info,
                "sex":sex,
            },
            success:function (resp) {
                if(resp==true){
                    alert("编辑成功")
                    window.location.href="http://localhost:8888/userinfo";
                }
            }
        })
    })

    //上传头像
    $("#upload-btn").click(function(){
        var formData = new FormData();
        var files = $("#files").prop("files");
        formData.append("files",files[0]);
        $.ajax({
            url: '/multifileUpload',  //server script to process data
            type: 'POST',
            xhr: function() {  // custom xhr
                myXhr = $.ajaxSettings.xhr();
                if(myXhr.upload){ // check if upload property exists
                    myXhr.upload.addEventListener('progress',progressHandlingFunction, false); // for handling the progress of the upload
                }
                return myXhr;
            },
            //Ajax事件
            // beforeSend: beforeSendHandler,
            success: function (resp) {
                console.log(resp);
                user.pic=resp;
                $("#headpic").attr("src",user.pic);
                alert("修改头像成功");
            },
            // error: errorHandler,
            // Form数据
            data: formData,
            //Options to tell JQuery not to process data or worry about content-type
            cache: false,
            contentType: false,
            processData: false
        });
        document.getElementById("files").style.display="none";
        document.getElementById("upload-btn").style.display="none";
        document.getElementById("choicehead").style.display="inline";
        document.getElementById("cancle-upload").style.display="none";
        document.getElementById("jindu").style.display="none";
    });

    //处理进度
    function progressHandlingFunction(e){
        if(e.lengthComputable){
            $('progress').attr({value:e.loaded,max:e.total});
        }
    }

    //点击更换头像
    $("#choicehead").click(function () {
        document.getElementById("files").style.display="inline";
        document.getElementById("upload-btn").style.display="inline";
        document.getElementById("cancle-upload").style.display="inline";
        document.getElementById("choicehead").style.display="none";
        $("#headpic").attr("src",user.pic)
        document.getElementById("jindu").style.display="inline";
    })
    //点击取消上传头像
    $("#cancle-upload").click(function () {
        document.getElementById("files").style.display="none";
        document.getElementById("upload-btn").style.display="none";
        document.getElementById("cancle-upload").style.display="none";
        document.getElementById("choicehead").style.display="inline";
        document.getElementById("jindu").style.display="none";
    })






eachgetData(1);

})

//分页
function each(CP) {
    eachhtml = "";
    $.ajax({
        type:"GET",
        url:"/blog/getUserBlogsPageMessage",
        data:{

        },
        success:function (resp) {
            //当前页
            var currentPage = CP;
            //分页数据 每页多少个
            var currentCount = resp["currentCount"];
            //总数
            var totalCount = resp["totalCount"];
            //总页数
            var totalPage = resp["totalPage"];
            //首页样式类
            var shouyeclass = "";
            //如果是第一页首页选项就不能不能点击
            if(currentPage==1){
                shouyeclass = "page-item disabled"
            }else {
                shouyeclass = "page-item"
            }
            //分页样式
            var eachclass = "";
            //eachgetData(1)是之后显示数据的方法
            eachhtml +="<li  class='"+shouyeclass+"' >" +
                "          <a class=\"page-link \"  href=\"javascript:void(0);\" onclick='eachgetData(1)' tabindex=\"-1\">首页</a>" +
                "       </li>"
            for (var i = 1 ; i <= totalPage;i++){
                if (currentPage == i){
                    eachclass = "page-item active"
                }else{
                    eachclass = "page-item"
                }
                eachhtml += " <li  class='"+eachclass+"' ><a class=\"page-link\" href=\"javascript:void(0);\" onclick='eachgetData("+i+")'>"+i+"<span class=\"sr-only\">(current)</span></a></li>"
            }
            if(currentPage!=totalPage){
                eachhtml+= "<li class=\"page-item\" >" +
                    "           <a class=\"page-link\" href=\"javascript:void(0);\" onclick='eachgetData("+(currentPage+1)+")'>下一页</a>" +
                    "       </li>"
            }else{
                eachhtml +="";
            }
            $("#eachdata").html(eachhtml);
        }
    })

}


function eachgetData(CP){
    //获取自己的故事
    datehtml="";
    $.ajax({
        type:"GET",
        url:"/blog/showUserBlogsCurrentPage",
        data:{
            "currentPage":CP,
        },
        success:function (resp) {
            console.log(resp)
            var i;
            for(i=0;i<resp.length;i++){
                datehtml=datehtml+"<div class=\"panel-body otherHeadPic\">\n" +
                    "            <div class=\"media\">\n" +
                    "                <div class=\"media-left media-middle\">\n" +
                    "                    <a href=\"#\">\n" +
                    "                        <img class=\"media-object img-circle\" src=\""+user.pic+"\" alt=\"...\">\n" +
                    "                    </a>\n" +
                    "                </div>\n" +
                    "                <div class=\"media-body\">\n <br>" +
                    "                    <h4 class=\"media-heading\">"+resp[i].blogName+"</h4>\n" +
                    "                    浏览量:<span>"+resp[i].visits+"</span> 点赞量:<span>"+resp[i].likenum+"</span>" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "        </div>"
            }
            each(CP)
            $("#test").html(datehtml);
        }
    })
}
