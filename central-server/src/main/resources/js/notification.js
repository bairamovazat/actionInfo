
let globalOptions = {
    labels: {
        success :"Успешно",
        alert: "Ошибка",
        async: "Загрузка",
        confirm: "Подтвердите действие",
        confirmOk: "ОК",
        confirmCancel: "Отмена"
    },
    messages: {
        "async-block": "Загрузка",
        async: "Обновление данных"
    }
};

var notifier = new AWN(globalOptions);

function addError(message) {
    notifier.alert(message, {durations: {alert: 5000}});
}

function addErrorFromResponse(error) {
    console.log(error);
    notifier.alert(error.responseJSON.message, {durations: {alert: 5000}});
}

function addSuccess(message) {
    notifier.success(message, {durations: {success: 5000}});
}

// function addMessage(message, block, time, className) {
//     let span = document.createElement("span");
//     span.innerText = message;
//     span.className = className;
//     span.innerHTML = span.innerHTML + "<br>";
//     span.style.display = "none";
//     $(block).append(span);
//     $(span).fadeIn('fast');
//     setTimeout(function () {
//         $(span).fadeOut('fast');
//     }, time);
// }