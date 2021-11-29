$(function () {

    eachgetData(1);
})

//分页
function each(CP) {
    eachhtml = "";
    $.ajax({
        type:"GET",
        url:"/blog/getAllUserBlogsPageMessage",
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

//获取每页的数据
function eachgetData(CP){
    //获取自己的故事
    datehtml="";
    $.ajax({
        type:"GET",
        url:"/blog/showAllBlogsCurrentPage",
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
                    "                        <img class=\"media-object img-circle\" src=\""+resp[i].head+"\" alt=\"...\">\n" +
                    "                    </a>\n" +
                    "                </div>\n" +
                    "                <div class=\"media-body\">\n <br>" +
                    "                    <a href=\""+"http://localhost:8888/blogInfo/"+resp[i].id+"\">\n" +
                    "                    <h4 class=\"media-heading\">"+resp[i].blogName+"</h4>\n" +
                    "                    </a>\n" +
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