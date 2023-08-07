% The purpose of the script was to extract information about the displacements
% of the plate attached to the positioning platform. Measurements were made by
% sending an continous acoustic sinus and recording it after reflection from
% an obstacle.The phenomenon of phase shift was used for the calculation
% purposes

close all
clc

%% data import
% first I drag the corresponding data into the Command Window
pos = cell2mat(data(:,1));
trans_sig = cell2mat(data(:,2));
rec_sig = cell2mat(data(:,3));
fs = 5000000;
t = (0:length(trans_sig)-1)./ fs;
lambda = 10^3*(331.5+0.6*25)/340000; %mm

%% main loop

for i = 1:16
    
%     %% Plotting successive pairs of signals from samples
%                 figure(i)
%                 plot (t, trans_sig(i,:), 'b', t, rec_sig(i,:), 'r')
%                 xlabel('Time [s]')
%                 ylabel('Amplitude [V]')
%     legend('Transmitted signal','Recived signal');
%                 title('Signals - Wave 2')
    
    %% calculating the phase difference and placing it in the [0.2*pi] range
    
    s1(i,:) = angle(hilbert(trans_sig(i,:))) - angle(hilbert(rec_sig(i,:)));
    s2(i,:) = unwrap(s1(i,:));
    
    phase_shift(i) = mean(s2(i,:));
    
    if(phase_shift(i)<0)
        phase_shift(i) = phase_shift(i)+2*pi;
    end
    
    %% position calculation
    
    position(i) = (phase_shift(i)*(lambda))/(4*pi);
    
end

%% determining of displacements

for j = 1:15
    
    % moving the samples by the length of the testing range when the next
    % position is relatively much larger than the previous one
    if(position(j+1)>position(j)+0.05)
        position(j+1:length(position)) = position(j+1:length(position))-lambda/2;
    end
    
    % calculations of displacements and comparison with the given ones
    displ(j) = position(j) - position(j+1);
    ref(j) = pos(j) - pos(j+1);
    displ_err(j) = abs(displ(j) - ref(j));
end

mean_err = mean(displ_err);


