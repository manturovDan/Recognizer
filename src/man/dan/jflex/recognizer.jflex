package man.dan.jflex;
%%

%class RecognizerJF
%unicode
%int
%debug
%line

%{

%}
    numbersReg = (([0-9]{11},)*)([0-9]{11});
    regExFT = (tel|fax): { numbersReg }
    regExSMS = sms: { numbersReg } (\?body=([0-9a-zA-Z%,.!?]{1,64}))?
%%

    { regExSMS } { System.out.println(yytext()); return 2; }
    { regExFT } { System.out.println(yytext()); return 1; }

    . { }
    \n { }

    <<EOF>>                            { return 3; }