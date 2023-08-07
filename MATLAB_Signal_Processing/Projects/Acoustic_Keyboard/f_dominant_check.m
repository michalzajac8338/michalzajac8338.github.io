% script used to check vessel frequency

clear all
close all
clc

fs = 24000;
tk = 2;                 % time of recording

%%
disp('hit the dish in');
pause(1);
disp('1');
pause(0.9);
clc
disp('0.1');
pause(0.1);
clc;

%% audio
AudioRec = audiorecorder(fs,24,1);
recordblocking(AudioRec,tk);
AUDIO = getaudiodata(AudioRec);
fvec = 0 : fs/length(AUDIO) : fs-(fs/length(AUDIO));

%% sorting
x(:,1) = abs(fft(AUDIO));
x(:,2) = fvec(1,:);
f_sorted = sortrows(x,1,'descend');
f_dominant = f_sorted(1,2);

fprintf('dominant freq of this vessel is %i',f_dominant);
fprintf(' [Hz]');