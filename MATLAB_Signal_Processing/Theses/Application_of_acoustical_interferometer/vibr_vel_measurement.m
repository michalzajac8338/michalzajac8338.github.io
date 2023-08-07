% The purpose of the script was to extract information about the vibration
% velocity of glass plate attached to modal exciter. At the beginning, the
% amplitude modulation was removed from the signal, based on the Hilbert
% transform. Next, frequency and phase demodulation were carried out,
% resulting in the dependence of the deflection of the plate on time.
% This signal was differentiated to obtain the desired vibration velocity.

clc
close all

%%
load('C:\Users\micha\Desktop\inzynierka\programy_i_dane\dane\100hz\data.mat')
for i = 1:size(data,1)
    ref(i) = 100;
end

% load('C:\Users\micha\Desktop\inzynierka\programy_i_dane\dane\50mvpp\data.mat')
% for i = 1:size(data,1)
%     ref(i) = 30 +i*10;
% end

% filter import
load('C:\Users\micha\Desktop\inzynierka\programy_i_dane\filtr.mat')

trans_sig = cell2mat(data(:,1));
rec_sig = cell2mat(data(:,2));
fs = 5000000;
t = (0:length(trans_sig)-1)./ fs;
s = length(t);
f = linspace(0,fs-fs/s,s);

for i = 1:size(trans_sig,1)
    
    y = double(rec_sig(i,:));
    
%     figure(1)
%     plot(t,y)
%     xlabel('Time [s]')
%     ylabel('Amplitude [V]')
%     title('Sygnal odebrany')
%     
%     figure(2)
%     semilogy(f/1e3,abs(fft(y)))
%     xlabel('Frequency [kHz]')
%     ylabel('Amplitude [dB]')
%     title('Recieved signal')
%     xlim([0,fs/2/1e3])
    
    %% BP filtration close to freq of the carrier
    y = bandpass(y,[3.35e5,3.45e5],fs);
    
%     figure(3)
%     plot(t,y)
%     xlabel('Time [s]')
%     ylabel('Amplitude [a.u.]')
%     title('Sygnal after BP filtration')
%     
%     figure(4)
%     semilogy(f/1e3,abs(fft(y)))
%     xlabel('Frequency [kHz]')
%     ylabel('Amplitude [a.u.]')
%     title('Sygnal after BP filtration')
%     xlim([0,fs/2/1e3])
    
    %% removing AM
    y_env = abs(hilbert(y));
    y_FM = y./y_env;
    
%     figure(5)
%     plot(t,y_FM)
%     xlabel('Time [s]')
%     ylabel('Amplitude [a.u.]')
%     title('Signal after amplitude demodulation')
%     
%     figure(6)
%     semilogy(f/1e3,abs(fft(y_FM))/s*2)
%     xlabel('Frequency [kHz]')
%     ylabel('Amplitude [a.u.]')
%     title('Signal after amplitude demodulation')
%     xlim([0,fs/2/1e3])
    
    %% FM demodulation (derivative)
    % derivative calculation
    y_dt = diff(y_FM);
    
    % shortening of time vector
    t1 = (0:length(y_dt)-1)./ fs;
    % envelope calculatiojn
    y_dt_env = abs(hilbert(y_dt));
    
%     figure(7)
%     plot(t1,y_dt)
%     xlabel('Time [s]')
%     ylabel('Amplitude [a.u.]')
%     title('differentiated signal')
%     hold on
%     plot(t1,y_dt_env,'r')
%     legend('signal','envelope')
    
    % const. component removing
    y_dem = y_dt_env- mean(y_dt_env);
    
    % vector shortening
    cut = 1.1e5;
    y_dem = y_dem(1+cut:end-cut);
    t1 = cut/fs+1/fs:1/fs:(s-cut)/fs-1/fs;
    s1 = length(t1);
    f1 = linspace(0,fs-fs/s1,s1);
    
%     figure(8)
%     plot(t1,y_dem)
%     xlabel('Time [s]')
%     ylabel('Amplitude [a.u.]')
%     title('Sygnal with const. component removed')
    
    % low-pass filtering with an imported filter
    y_filt = filtfilt(Num,1,y_dem); %lowpass(y_dem, 300, fs);
    
%     figure(9)
%     plot(t1,y_filt)
%     xlabel('Time [s]')
%     ylabel('Amplitude [a.u.]')
%     title('Signal after lowpass filtering')
    
    % signal integration
    y_ampl = inteFD(y_filt,1/fs)*s1/2;
    y_ampl = detrend(y_ampl);
    
%     figure(10)
%     plot(t1,y_ampl)
%     xlabel('Time [s]')
%     ylabel('Amplitude [a.u.]')
%     title('signal after integration')
%     
%     figure(11)
%     semilogy(f1/1e3,abs(fft(y_ampl))/s1*2)
%     xlabel('Frequency [kHz]')
%     ylabel('Amplitude [a.u.]')
%     title('signal after integration')
%     xlim([0,fs/2/1e3])
    
    y_vel = diff(y_ampl)*fs;
    t2 = (0:length(y_vel)-1)./ fs;
    s2 = length(t2);
    f2 = linspace(0,fs-fs/s2,s2);
    
%     figure(12)
%     plot(t2,y_vel)
%     xlabel('Time [s]')
%     ylabel('Vibration velocity [a.u.]')
%     title('signal after integration')
%     
%     figure(13)
%     semilogy(f2/1e3,abs(fft(y_vel))/s2*2)
%     xlabel('Frequency [kHz]')
%     ylabel('Vibration velocity [a.u.]')
%     title('signal after integration')
%     xlim([0,fs/2/1e3])
    
    %% looking for dominant freq of a glass plate
    [y,x] = max(abs(fft(y_ampl)));
    f_dom_gp(i) = f1(x);
    
    % determinig of freq error
    f_abs_err(i) = f_dom_gp(i) - ref(i);
    
    % amplitude determining in a.u.
    A = (abs(y_ampl));
    TF = islocalmax(A);
    ampl(i) = mean(A(TF));
    
    % velocity determinig in a.u.
    B = abs(y_vel(5000:end-5000));
    TF1 = islocalmax(B, 'FlatSelection', 'first');
    m = sort(B(TF1), 'descend');
    largest = m(1:length(A(TF)));
    v(i) = mean(largest);
    i
end

for i = 1:size(trans_sig,1)
    data(i) = i;
end

figure()
plot(data,ampl,'r-')
xlabel('Data')
ylabel('Amplitude [a.u.]')
title('Amplitude (Data)')
xlim([1 20])

figure()
plot(data,v,'r-')
xlabel('Data')
ylabel('Velocity [a.u.]')
title('Amplitude (Data)')
xlim([1 20])



