var ajaxUrl = "ajax/admin/users/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});

function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}

function al(enabled, id) {

    if(enabled) {
        alert(id + ' enabled');
        $.ajax({
            type: "POST",
            url: ajaxUrl + "setEnDis/" + id,
            data: {
                state: "false"
            },
            success: function () {
                updateTable();
                successNoty("State updated");
            }
        });

    } else {
        alert(id + ' disabled');
        $.ajax({
            type: "POST",
            url: ajaxUrl + "setEnDis/" + id,
            data: {
                state: "true"
            },
            success: function () {
                updateTable();
                successNoty("State updated");
            }
        });
    }

// reload page (update table)
}
