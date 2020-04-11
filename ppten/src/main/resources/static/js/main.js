var global = {};

$(document).ready(function getPage() {
    updatePage();
});

function updatePage() {
    $.getJSON("/ajax", function (data, textStatus, jqXHR) {
        var out = "";
        global.users = data;
        var i = 0;
        jQuery.each(data, function (key, value) {
            out += `<tr id="${value.id}"> 
            <td class="uid"> ${(++i)}</td>
            <td class="uname">${value.username}</td>
            <td class="role"> ${value.roles[0].userRoles}</td>
            <td> 
            <button onclick="edit(${value.id})" type="button" id = "edit12" class="btn btn-info" data-toggle="modal"
             data-target="#myModal">Edit</button>&ensp;
            <input onclick="del(${value.id})" type="button" value="delete" class="btn btn-danger"> </td> `;
        });
        jQuery("#table").html(out);
    });
}

var s = 0;

function edit(id) {

    var user = global.users.find(function (user) {
        return parseInt(user.id) === parseInt(id);
    });

    if (user && user.id) {
        $("#id").val(user.id);
        $("#uname").val(user.username);
        if (user.roles[0].userRoles == 'ROLE_ADMIN') {
            $("#option1").attr('selected', 'selected');
        } else {
            $("#option2").attr('selected', 'selected');
        }
    }
}

function del(id) {
    $.ajax({
        type: "GET",
        url: '/ajax/' + id,
        success: function (data) {
            $('#' + id).remove();
        }
    });
};

function update() {

    var id = $("#id").val();
    var username = $("#uname").val();
    var password = $("#changepass").val();
    var role = $("#role").val();
    // console.log(role);
    $.post(
        "/update", {
            id: id,
            username: username,
            password: password,
            role: role
        },
        onAjaxSuccess
    );

    function onAjaxSuccess(data) {
        if (data == "error") {
            $("#errorM").css('display', 'block');
        } else {
            updatePage();
            $("#myModal").closest('.modal').modal('hide');
        }
    }
};
function hide() {
    $("#errorM").css('display', 'none');
}
// $("#save").click(function () {
//     $("#myModal").closest('.modal').modal('hide');
// });

function addUser() {

    var username = $("#username").val();
    var password = $("#password").val();
    var password1 = $("#password1").val();
    var role = $("#roles").val();
    // console.log(role);
    $.post(
        "/ajax", {
            username: username,
            password: password,
            password1: password1,
            role: role
        },
        onAjaxSuccess
    );

    function onAjaxSuccess(data) {
        if (data == "Error1") {
            $("#error1").css('display', 'block');
        } else if (data == "Error2") {
            $("#error2").css('display', 'block');
        } else if (data == "Error3") {
            $("#error3").css('display', 'block');
        } else {
            $("#username").val('');
            $("#password").val('');
            $("#password1").val('');
            $("#okadd").css('display', 'block');
            updatePage();
            setTimeout(kick, 3000);
            // $("#roles").val();

        }
    }
}

$("#password1,#password,#username").click(
    function clear() {
        $("#error1").css('display', 'none');
        $("#error2").css('display', 'none');
        $("#error3").css('display', 'none');
    });

function kick() {
    $("#okadd").css('display', 'none');
}