% The purpose of the script was to extract information about the displacements
% of the plate attached to the positioning platform. Measurements were made by
% sending an acoustic impulse and recording it after reflection from an obstacle.
% The phenomenon of signal envelope was used for the calculation purposes

close all
clc

%% data import
% first I drag the corresponding data into the Command Window
pos = cell2mat(data(:,1));
trans_sig = cell2mat(data(:,2));
rec_sig = cell2mat(data(:,3));
fs = 5000000;
t_exact = vpa((0:length(trans_sig)-1) ./ fs);
t = (0:length(trans_sig)-1)./ fs;

%% main loop

for i = 16 %:16
%     figure(2*i-1)
%     plot (t, trans_sig(i,:), 'b', t, rec_sig(i,:), 'g');
%     xlabel('Time [s]')
%     ylabel('Amplitude [V]')
%     legend('Transmitted signal','Recived signal');
%     title('Signals- Wave 1')
    
    %% looking for max of the envelope
    
    env_trans = abs(hilbert(trans_sig(i,:)));
    env_rec = abs(hilbert(rec_sig(i,:)));
    
    figure(2*i)
    plot(t,trans_sig(i,:),'b', t, env_trans, t, rec_sig(i,:),'g', t, env_rec)
    legend('Transmitted signal', 'env. of trans. sig', 'Recieved signal','env. of rec. sig.')
    xlabel('Time [s]')
    ylabel('Amplitude [V]')
    title('Sygnals with envelopes')
    
    %% division of the envelope in half - searching for the transmitted pulse(s)
    env_t_1 = env_trans(1,1:length(env_trans)/2);
    env_t_2 = zeros(1,length(env_trans));
    env_t_2(1,1+length(env_trans)/2:length(env_trans)) = env_trans(1,1+length(env_trans)/2:length(env_trans));
    
    [x1,y1] = max(env_t_1);
    [x2,y2] = max(env_t_2);
    
    % finds the first pulse - thanks to this I have a pulse determined at position 0
    if(x2 > 5*x1)
        x = x2;
        y = y2;
        mic = env_rec(y:length(env_rec));
    else
        x = x1;
        y = y1;
        mic = env_rec(y:y+17232);
    end
    
    t_peak = t_exact(y);
    
    %% signal received
    
    % width of t for the impulse: 4*T
    dt = 4/(340*10^3);
    db = ceil((length(t)*dt)/(t(length(t))));
    
    % find 2 maxes not closer than 2 pulse widths and look at their relationship
    for k = 1:2
        [a(k),b(k)] = max(mic);
        mic(b(k)-2*db:b(k)+2*db) = zeros(1,4*db+1);
    end
    
    %I am interested in the largest max, unless it is the second bounce
    A = a(1);
    B = b(1);

    if  ((2*b(2) > b(1)-db && 2*b(2) < b(1)+db) && 2*a(2)>a(1))
        A = a(2);
        B = b(2);
    end
    
    measured_pos(i) = vpa(((t_exact(B+y)-t_exact(y)) * 10^3 * (331.5 + 0.6 *25))/2);
    pos_err(i) = double(measured_pos(i)-pos(i));
end

%% pomiar kolejnych przemieszczen

for j = 1:15
    displacement(j) = double(measured_pos(j) - measured_pos(j+1));
    ref(j) = pos(j) - pos(j+1);
    disp_err(j) = abs(displacement(j) - ref(j));
end

mean_err = mean(disp_err);
