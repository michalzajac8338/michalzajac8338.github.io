% assigning function: f->letter

function [letter] = alphabet_1(f_dominant)

letter = 1;

%% letter display

if f_dominant >= 2040 && f_dominant < 2070
    fprintf('A');
elseif f_dominant >= 93.5 && f_dominant < 100
    fprintf('B');
elseif f_dominant >= 6500 && f_dominant < 6800
    fprintf('C');
elseif f_dominant >= 5350 && f_dominant < 5390
    fprintf('D');
elseif f_dominant >= 1250 && f_dominant < 1350
    fprintf('E');
elseif f_dominant >= 170 && f_dominant < 176
    fprintf('F');
elseif f_dominant >= 6840 && f_dominant < 6860
    fprintf('G');
elseif f_dominant >= 500 && f_dominant < 550
    fprintf('H');
elseif f_dominant >= 2530 && f_dominant < 2540
    fprintf('I');
elseif f_dominant >= 6800 && f_dominant < 8000
    fprintf('J');
elseif f_dominant >= 250 && f_dominant < 260
    fprintf('K');
elseif f_dominant >= 2800 && f_dominant < 2850
    fprintf('L');
elseif f_dominant >= 900 && f_dominant < 1000
    fprintf('M');
elseif f_dominant >= 2960 && f_dominant < 2970
    fprintf('N');
elseif f_dominant >= 1500 && f_dominant < 1600
    fprintf('O');
elseif f_dominant >= 8000 && f_dominant < 8300
    fprintf('P');
elseif f_dominant >= 464 && f_dominant < 470
    fprintf('R');
elseif f_dominant >= 180 && f_dominant < 210
    fprintf('S');
elseif f_dominant >= 2275 && f_dominant < 2290
    fprintf('T');
elseif f_dominant >= 350 && f_dominant < 400
    fprintf('U');
elseif f_dominant >= 630 && f_dominant < 660
    fprintf('W');
elseif f_dominant >= 1800 && f_dominant < 1900
    fprintf('Y');
elseif f_dominant >= 590 && f_dominant < 600
    fprintf('Z');
elseif f_dominant >= 177 && f_dominant < 180
    fprintf('_');
else
    letter = 0;
end