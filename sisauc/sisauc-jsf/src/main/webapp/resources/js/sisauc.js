function maskSuffix(suffix){
	$('.number').maskMoney({
		suffix: suffix,
		thousands: '.',
		decimal: ',',
		allowZero: true,
		allowNegative: true
	});
}

function maskPrefix(prefix){
	$('.number').maskMoney({
		prefix: prefix,
		thousands: '.',
		decimal: ',',
		allowZero: true,
		allowNegative: true
	});
}

function maskPrefixOnlyPositiveNumbers(prefix){
	$('.number').maskMoney({
		prefix: prefix,
		thousands: '.',
		decimal: ',',
		allowZero: true,
		allowNegative: false
	});
}


function unmask(sufix){
	$('.number').maskMoney('destroy');
}

function isCapslock(e){

    e = (e) ? e : window.event;

    var charCode = false;
    if (e.which) {
        charCode = e.which;
    } else if (e.keyCode) {
        charCode = e.keyCode;L
    }

    var shifton = false;
    if (e.shiftKey) {
        shifton = e.shiftKey;
    } else if (e.modifiers) {
        shifton = !!(e.modifiers & 4);
    }

    if (charCode >= 97 && charCode <= 122 && shifton) {
    	$('.capslock-alert').show();
        return true;
    }

    if (charCode >= 65 && charCode <= 90 && !shifton) {
    	$('.capslock-alert').show();
        return true;
    }

    $('.capslock-alert').hide();
    return false;

}

function abrirSobre(usuario, url){
	$.cookie(usuario, 'false');	
	window.location.href = url;
}



(function($) {
    var contentHeight = 190;
    $('#footer').css('bottom',-contentHeight);
    var hidden = true,animating = false;
    
    $('#footer .handle a').click(function(e) {
        e.preventDefault();
        animating = true;
        if(hidden) {
            $('#footer').animate({
                    bottom:0
                },400,'easeOutCirc',function() {
                    animating = false;
                    hidden = false;
                    //$('#footer .handle a').html("Close");
            });
        } else {
            $('#footer').animate({
                    bottom:-contentHeight
                },200,'easeOutCirc',function() {
                    animating = false;
                    hidden = true;
                    //$('#footer .handle a').html("Open");
            });
        }
            
    });
}(jQuery))


