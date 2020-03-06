package man.dan.jflex;
%%

%class RecognizerJF
%unicode
%int
%debug

%{

%}
    numbersReg = ([0-9]{11},)*([0-9]{11})
    regExFT = tel|fax: { numbersReg }
    regExSMS = sms: { numbersReg } \\?body=[0-9a-zA-Z%,.!?]{1,64}?;
%%

    { regExFT } { System.out.println("FT"); return 1; }
    { regExSMS } { System.out.println("SMS"); return 2; }

    . { System.out.println("ns"); return 4; }

    <<EOF>>                            { return 3; }