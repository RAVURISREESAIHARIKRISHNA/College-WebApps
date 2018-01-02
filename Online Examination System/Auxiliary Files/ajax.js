var xhr;    //GLOBAL
function create(){
    xhr = new XMLHttpRequest();
    draw1Question();
}
function removeCookies(){
    // alert("You are Quiting Exam!!");
    document.cookie = "roll" + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    
    window.close();
}
function draw1Question(){
    let ques = document.getElementById("question");
    ques.innerHTML = questions[0];
    let opt1 = document.getElementById("option1Label");
    opt1.innerHTML = options_1[0];
    let opt2 = document.getElementById("option2Label");
    opt2.innerHTML = options_2[0];
    let opt3 = document.getElementById("option3Label");
    opt3.innerHTML = options_3[0];
    let opt4 = document.getElementById("option4Label");
    opt4.innerHTML = options_4[0];
    document.getElementById("qnumber").innerHTML = 1;
}
var questions=[
    "Question 1",
    "Question 2",
    "Question 3"
];

var options_1 = [
    "Option 5",
    "Option 9",
    "Opption 13"
];

var options_2=[
    "Option 6",
    "Option 10",
    "Option 14"
];

var options_3=[
    "Option 7",
    "Option 11",
    "Option 15"
];

var options_4=[
    "Option 8",
    "Option 12",
    "OPtion 16"
];

var next_ = false;

function computeValue(){
    let arr = ["option1","option2","option3","option4"];
    for(let i=0;i<=3;i++){
        let obj = document.getElementById(arr[i]);
        if(obj.checked){
            return obj.value;
        }
    }
    return "x";
}

function makeRequest(){
    var value = computeValue();
    var url="http://localhost:8888/ServletServe/AjaxDemo?option="+value+"&ques="+document.getElementById("qnumber").innerHTML+"&roll="+getRollFromCookie();
    xhr.open("GET",url,true);
    xhr.onreadystatechange = doUpdate;
    xhr.send(null); //Make GET Request
    
}

function doUpdate(){
    if(xhr.readyState == 4){
        console.log("SUCCESS") ;
    }
}

function next(){
    next_ = true;
    console.log("NEXT Clicked");
    drawNextQuestion();
    makeRequest();
}
function prev(){
    next_ = false;
    console.log("PREVIOUS Clicked");
    drawPrevQuestion();
    makeRequest();
}

function drawNextQuestion(){
    console.log("drawNextQuestion () Called....");
    let qno = document.getElementById("qnumber").innerHTML;
    console.log(qno);
    if(qno < questions.length){
        let ques = document.getElementById("question");
        ques.innerHTML = questions[qno];
        let opt1 = document.getElementById("option1Label");
        console.log(opt1);
        opt1.innerHTML = options_1[qno];
        let opt2 = document.getElementById("option2Label");
        opt2.innerHTML = options_2[qno];
        let opt3 = document.getElementById("option3Label");
        opt3.innerHTML = options_3[qno];
        let opt4 = document.getElementById("option4Label");
        opt4.innerHTML = options_4[qno];
        document.getElementById("qnumber").innerHTML = ++qno;
        
    }
    
}

function drawPrevQuestion(){
    console.log("drawPrevQuestion() Called");
    let qno = document.getElementById("qnumber").innerHTML;
    if(qno > 1){
        let ques = document.getElementById("question");
        ques.innerHTML = questions[qno-2];
        let opt1 = document.getElementById("option1Label");
        opt1.innerHTML = options_1[qno-2];
        let opt2 = document.getElementById("option2Label");
        opt2.innerHTML = options_2[qno-2];
        let opt3 = document.getElementById("option3");
        opt3.innerHTML = options_3[qno-2];
        let opt4 = document.getElementById("option4Label");
        opt4.innerHTML = options_4[qno-2];
        document.getElementById("qnumber").innerHTML = --qno;
        
    }
}

function getRollFromCookie(){
    return Number(document.cookie.substring(document.cookie.indexOf("roll=")+5,document.cookie.length));
}