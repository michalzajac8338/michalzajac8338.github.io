close all
clear all
clc

fs = 8e6;
t = 0:1/fs:0.1-1/fs;

% zakresy dla obu:
% f sygnalu modulujacego - od 1e3 do 1e4
% f nosna 1e5:1e6
%% informacja
% a = 1 => stala amplituda
% a > 1 => narastajaca amplituda
% a < 1 => malejaca amplituda
% b - poczatkowa amplituda

a = 1000;
b = 1;
A1 = a*t+b;
f1 = 1000;

%% sin
s1 = A1.*sin(2*pi*f1*t);

%% chirp
% s1 = A1.*chirp(t,f1-500,t(length(t)),f1+500);

%% szum
% s1 = randn(1,length(t));
% s1 = A1.*s1;
% s1 = bandpass(s1,[f1-500,f1+500],fs);

%% f nosna
a = 1e5;
b = 1e6;
f2 = round(a + (b-a).*rand(1),-1);
s2 = sin(2*pi*f2*t);

%% sygnal zmodulowany
s_am = s1.*s2 + s2;

figure()
plot(t,s2, t,s_am,t,s1)
legend( 'modulowany', 'zmodulowany','modulujacy')
xlabel('czas [s]')
ylabel('amplituda [~]')
title('sygnaly')
xlim([0 0.01])
N = length(s_am);

%% wlasny hilbert
s_am_f = fft(s_am);

s_am_hf = zeros(1,length(s_am));
for k = 1:(N+1)/2
    s_am_hf(k) = -1i*sign((N+1)/2 - k)*sign(k)*s_am_f(k);
end

s_am_h = ifft(s_am_hf) *2;
env1 = abs(s_am_h);

figure()
plot(t,s_am, t,env1)
legend('zmodulowany', 'obwiednia')
xlabel('czas [s]')
ylabel('amplituda [~]')
title('sygnal')
xlim([0 0.01])
A11 = min(s_am(1:9e4));
A12 = max(s_am(1:9e4));
ylim([A11,A12])

% demodulacja
s_dem = s_am ./ env1;
figure()
plot(t,s_dem)
xlabel('czas [s]')
ylabel('amplituda [~]')
title('sygnal zdemodulowany')
ylim([-1.1 1.1])
xlim([0 0.01])

a = env1;%-mean(env1);
plot(t,s1,'ro',t,a,'g.')
xlim([0 0.01])