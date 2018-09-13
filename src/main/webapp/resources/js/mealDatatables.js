var ajaxUrl = "ajax/admin/meals/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#mealdatatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
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
    var sD = $('#startDate').val();
    var eD = $('#endDate').val();
    var sT = $('#startTime').val();
    var eT = $('#endTime').val();

    $.get(ajaxUrl + "filter?startDate=" + sD + "&startTime=" + sT + "&endDate=" + eD + "&endTime=" + eT, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}

function resetFilterForm() {
    $("#filterForm").find(":input").val("");
    return false;
}
