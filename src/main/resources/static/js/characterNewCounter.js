$('#shortDescription').keyup(function() {

    var characterCount = $(this).val().length,
        current1 = $('#current1'),
        maximum1 = $('#maximum1'),
        theCount1 = $('#the-count1');

    current1.text(characterCount);


    /*This isn't entirely necessary, just playin around*/
    if (characterCount < 70) {
        current1.css('color', '#666');
    }
    if (characterCount > 70 && characterCount < 90) {
        current1.css('color', '#6d5555');
    }
    if (characterCount > 90 && characterCount < 100) {
        current1.css('color', '#793535');
    }
    if (characterCount > 100 && characterCount < 120) {
        current1.css('color', '#841c1c');
    }
    if (characterCount > 120 && characterCount < 139) {
        current1.css('color', '#8f0001');
    }

    if (characterCount >= 140) {
        maximum1.css('color', '#8f0001');
        current1.css('color', '#8f0001');
        theCount1.css('font-weight','bold');
    } else {
        maximum1.css('color','#666');
        theCount1.css('font-weight','normal');
    }


});