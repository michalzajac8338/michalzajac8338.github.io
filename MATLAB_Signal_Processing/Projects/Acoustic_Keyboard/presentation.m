% this script allows you to type on the keyboard in real time

clear all
close all
clc

%% specifying the parameters
fs = 24000;             % signal sampling frequency in Hz
tkp = 4;                % audio recording time when selecting an interval
tk = 3;                 % audio recording time while typing

%% keyboard
disp('start typying in');
pause(3);
clc;
disp('3');
pause(1);
clc;
disp('2');
pause(1);
clc
disp('1');
pause(0.9);
clc
disp('0.1');
pause(0.1);
clc;

a = 0;
EndingCondition = 0;
while (EndingCondition == 0)
    
    %% audio
    AudioRec = audiorecorder(fs,24,1);
    recordblocking(AudioRec,tk);
    AUDIO = getaudiodata(AudioRec);
    fvec = 0 : fs/length(AUDIO) : fs-(fs/length(AUDIO));
    
    %% sortowanko
    x(:,1) = abs(fft(AUDIO));
    x(:,2) = fvec(1,:);
    f_sorted = sortrows(x,1,'descend');
    f_dominant = f_sorted(1,2);
    
    if alphabet_1(f_dominant) == 0
        a = a + 1;
        if a == 3
            fprintf('\nDziêkujemy za skorzystanie z klawiatury');
            break;
        end
    else
        a = 0;
    end
end