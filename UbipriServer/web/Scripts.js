/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



function validaform() {
    var d = document.querySelectorAll('input[type=text]');

    for (var i = 0; i < d.length; i++)
        if (d[i].value == "") {
            document.getElementById("mensagem").innerHTML = 'Todos os campos são obrigatorios.';
            d[i].focus();
            return false;
        }
}

function toggle(source) {
    checkboxes = document.querySelectorAll('input[type=checkbox]');
    for (var i = 0, n = checkboxes.length; i < n; i++) {
        checkboxes[i].checked = source.checked;
    }
}


function confirmSubmit()
{
    checkboxes = document.querySelectorAll('input[type=checkbox]');
    for (var i = 0, n = checkboxes.length; i < n; i++) {
        if (checkboxes[i].checked == true) {
            var agree = confirm("Deseja remover os selecionados?");
            if (agree)
                return true;
            else
                return false;
        }
    }
    return false;
}


function Pager(tableName, itemsPerPage) {
    this.tableName = tableName;
    this.itemsPerPage = itemsPerPage;
    this.currentPage = 1;
    this.pages = 0;
    this.inited = false;

    this.showRecords = function(from, to) {

        var rows = document.getElementById(tableName).rows;

// i starts from 1 to skip table header row

        for (var i = 1; i < rows.length; i++) {

            if (i < from || i > to)
                rows[i].style.display = 'none';

            else
                rows[i].style.display = '';

        }

    }

    this.showPage = function(pageNumber) {

        if (!this.inited) {

            alert("not inited");

            return;

        }

        var oldPageAnchor = document.getElementById('pg' + this.currentPage);

        oldPageAnchor.className = 'pg-normal';

        this.currentPage = pageNumber;

        var newPageAnchor = document.getElementById('pg' + this.currentPage);

        newPageAnchor.className = 'pg-selected';

        var from = (pageNumber - 1) * itemsPerPage + 1;

        var to = from + itemsPerPage - 1;

        this.showRecords(from, to);

    }

    this.prev = function() {

        if (this.currentPage > 1)
            this.showPage(this.currentPage - 1);

    }

    this.next = function() {

        if (this.currentPage < this.pages) {

            this.showPage(this.currentPage + 1);

        }

    }

    this.init = function() {

        var rows = document.getElementById(tableName).rows;

        var records = (rows.length - 1);

        this.pages = Math.ceil(records / itemsPerPage);

        this.inited = true;

    }

    this.showPageNav = function(pagerName, positionId) {
        var rows = document.getElementById(tableName).rows;

        if (!this.inited) {

            alert("not inited");

            return;

        }

        var element = document.getElementById(positionId);

        if(rows.length > 10){
        var pagerHtml = '<span onclick="' + pagerName + '.prev();" class="pg-normal"> Prev </span> ';

        for (var page = 1; page <= this.pages; page++)
            pagerHtml += '<span id="pg' + page + '" class="pg-normal" onclick="' + pagerName + '.showPage(' + page + ');">' + page + '</span> ';

        pagerHtml += '<span onclick="' + pagerName + '.next();" class="pg-normal"> Next </span>';

        element.innerHTML = pagerHtml;
        }
    }

}