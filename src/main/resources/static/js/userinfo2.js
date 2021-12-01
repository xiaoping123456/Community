$(function () {
    document.getElementById("mylike").style.display="none";
    document.getElementById("mystory").style.display="none";
})


document.getElementById("mystory-click").onclick=function () {
    document.getElementById("personinfo").style.display="none";
    document.getElementById("mylike").style.display="none";
    document.getElementById("mystory").style.display="inline";
    document.getElementById("personinfo-li").className="";
    document.getElementById("mystory-li").className="active";
    document.getElementById("mylike-li").className="";
}

document.getElementById("mylike-click").onclick=function () {
    document.getElementById("personinfo").style.display="none";
    document.getElementById("mylike").style.display="inline";
    document.getElementById("mystory").style.display="none";
    document.getElementById("personinfo-li").className="";
    document.getElementById("mystory-li").className="";
    document.getElementById("mylike-li").className="active";
}

document.getElementById("personinfo-click").onclick=function () {

    document.getElementById("personinfo").style.display="inline";
    document.getElementById("mylike").style.display="none";
    document.getElementById("mystory").style.display="none";
    document.getElementById("personinfo-li").className="active";
    document.getElementById("mystory-li").className="";
    document.getElementById("mylike-li").className="";
}
