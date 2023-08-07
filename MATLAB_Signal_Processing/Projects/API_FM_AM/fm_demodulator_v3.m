close all
clear all
clc

fs = 4e6;
t = 0:1/fs:0.05;
fvec = 0: fs/length(t) : fs-fs/length(t);

% definicja sygnalu nosnego
a = 1e5;
b = 1e6;
% fc = round(a + (b-a).*rand(1),-2);
fc = 2e5;
s2 = sin(2*pi*t*fc);

% sygnal modulujacy
f1 = 10000; % 1e3:1e4

%% sin
% s1 = sin(2*pi*f1*t);

%% chirp
% s1 = chirp(t,f1-500,t(length(t)),f1+500);

%% szum
s1 = randn(1,length(t));
s1 = bandpass(s1,[f1-500,f1+500],fs);

plot(t,s1);
title('Sygnal modulujacy');
xlabel('Czas [s]');
ylabel('Amplituda [~]')

figure()
semilogy(fvec,abs(2*fft(s1)/length(t)));
title('Sygnal przed modulacja');
xlabel('Czestotliwosc [Hz]');
ylabel('Amplituda [~]')
% xlim([0 fvec(length(fvec))/2])
xlim([f1-2e3 f1+2e3])

%% modulacja
s_fm = sin(2*pi*t*fc + s1);

figure()
plot(t,s_fm);
title('Zmodulowany sygnal');
xlabel('Czas [s]');
ylabel('Amplituda [~]')

figure()
semilogy(fvec,abs(2*fft(s_fm)/length(t)));
title('Zmodulowany sygnal');
xlabel('Czestotliwosc [Hz]');
ylabel('Amplituda [~]')
xlim([0 fvec(length(fvec))/2])

%% demodulacja FM
s_diff = diff(s_fm);
fvec1 = fvec(1:length(fvec)-1);

% hilbert
s_fm_f = fft(s_diff);
N = length(s_fm_f);

s_fm_hf = zeros(1,length(s_fm));
for k = 1:(N+1)/2
    s_fm_hf(k) = -1i*sign((N+1)/2 - k)*sign(k)*s_fm_f(k);
end

s_fm_h = ifft(s_fm_hf) *2;
env1 = abs(s_fm_h);

% dalsza demodulacja
Flow = f1-750;
Fhigh = f1+750;

env1 = env1 - mean(env1);
s_filt  = bandpass(env1,[Flow,Fhigh],fs);

s = length(t);
s_demod = inteFD(s_filt,1/fs)*s/2;            %calkowanie dla kompensacji zmiany fazy

figure()
plot(t,s_demod);
title('Sygnal po demodulacji');
xlabel('Czas [s]');
ylabel('Amplituda [~]')

figure()
semilogy(fvec,abs(2*fft(s_demod)/length(t)));
title('Sygnal po demodulacji');
xlabel('Czestotliwosc [Hz]');
ylabel('Amplituda [~]')
xlim([f1-2e3 f1+2e3])
% xlim([0 fvec(length(fvec))/2])
% [x,y] = max(abs(2*fft(s_demod)/length(t)));
% fvec(y)