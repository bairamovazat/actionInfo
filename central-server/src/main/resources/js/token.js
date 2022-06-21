let deleteTokenId;
let deleteTokenName;

$(document).on("click", ".delete-token-modal", function () {
    let tokenId = $(this).data('id');
    let tokenName = $(this).data('name');
    deleteTokenId = tokenId;
    deleteTokenName = tokenName;
    $("#tokenNameSpan").text(tokenName);
    $('#deleteForm').attr('action', 'token/' + deleteTokenId + '/delete');
    $("#deleteTokenName").val("");

});

$('#deleteTokenButton').click(function () {
    let deleteToken = {};
    deleteToken["id"] = deleteTokenId;
    deleteToken["name"] = deleteTokenName;
    if ($("#deleteTokenName").val() !== deleteToken["name"]) {
        addError("Название удаляемого токена неверно!");
        return false;
    }
    let promise = $.ajax({
        url: '#',
        contentType: "application/json",
        data: JSON.stringify(deleteToken),
        type: 'DELETE'
    });

    notifier.asyncBlock(promise,
        (response) => {
            addSuccess("Токен удалён");
            removeToken(deleteToken["id"]);
            // updateServerList();
        },
        (error) => {
            addError("Ошибка удаления токена");
        },
    )
});

function removeToken(id) {
    $("#token-" + id).hide();
}