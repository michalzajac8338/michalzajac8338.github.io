% Processing the results using this script is to plot the directional
% characteristics of subsequent transducers.
% 
% In each iteration of the loop, the results for a different force are
% loaded.
% In the first step of the loop, the signal sampling rate is determined,
% based on the imported time vector. Then, the data on the location of the
% measurement points are converted into their equivalents in polar
% coordinates, the center of which is determined in the center of the
% transmitter.

% Then, after low-pass filtering, appropriately adjusted to the applied
% excitation, the maxima of the envelope of selected data sets for
% subsequent directions are searched. They determine the sought intensity
% distribution, depending on the angle in the previously defined polar
% coordinate system. After their calculation, sorting and smoothing of the
%charts using the median, the directional characteristics calculated in
% this way are plotted.


clear all
close all
clc

%%
p2l = './MFC_ALU\M-2807-P2\'; %M-0714-P2
% FN = {'80kHz_5cyc.svd'; '100kHz_5cyc.svd'; '150kHz_5cyc.svd'; '200kHz_5cyc.svd'; 'broadband.svd'};
FN = {'80kHz_5cyc_composite.svd'; '100kHz_5cyc_composite.svd'; '150kHz_5cyc_composite.svd'; '200kHz_5cyc_composite.svd'; 'broadband_composite.svd'};
LOWPASS_FILTER = [100e3 130e3 180e3 235e3 0];
j = {'80kHz' , '100kHz' ,'150kHz' ,'200kHz', 'broadband'};

for i = 1:5
    % fn = FN(i,:);
    fn = FN{i};
    
    LOWPASS_FILTER = LOWPASS_FILTER(i);
    
    %% aluminum
    % M2814
%     if(i~=2) xyz_ = 0.0252; end
%     if(i==2) xyz_ = 0.0025; end
    % M2807
%     xyz_ = 0;
    % M0714
%     xyz_ = 0.0091;
    
    %% composite
    % M2814
%     xyz_ = 0.0252;
    % M2807
%     xyz_ = 0.0202;
    % M0714
%         xyz_ = 0.0094;
%     xyz_ = 0.02; %?Piezo
    
    load([p2l  fn '.mat'])
    t = VibData.t;
    fs = 1/(t(2)-t(1));
    %%
    
    xyz = VibData.XYZ(:,1:2);
    theta = -30 *pi/180;
    R = [cosd(theta) -sind(theta); sind(theta) cosd(theta)];
    
    xyz = xyz * R;
    
    xyz(:,2) =  xyz(:,2)-xyz_;
    
    %% select points at a given distance
    
    r = sqrt(xyz(:,1).^2 + xyz(:,2).^2);
    theta = atan2(xyz(:,2),xyz(:,1));
    
    sel = find(r<=0.1 & r >= 0.09);
    
%     figure(i)
%     plot(xyz(:,1), xyz(:,2),'.', xyz(sel,1), xyz(sel,2),'s')
%     axis equal
%     xlabel('x [m]')
%     ylabel('y [m]')
%     title('selected measurement points')
%     set(gca, 'fontsize', 12), box off
    %%
    
    % perfrom low-pass filtration
    data = VibData.x(sel,1:800)';
    if(i<5) data = lowpass(data,LOWPASS_FILTER,fs); end
    t = VibData.t(1:800);
    
    selectedTheta = theta(sel);
    maxVal = max(abs(hilbert(data)));
%     maxVal(maxVal>0.004) = 0;
    
    %% sorting and median
    
    stheta = [theta(sel), maxVal'];
    stheta = sortrows(stheta,1);
    stheta(:,2) = medfilt1(stheta(:,2),5);
    
%         if (i<5)
%             figure(1)
%             subplot(2,2,i)
%             polar(stheta(:,1),stheta(:,2),'.')
%             title(j{i})
%         else
%             figure(2)
%             polar(stheta(:,1),stheta(:,2),'.')
%             title(j{i})
%         end
%     
    figure(i+1)
    polar(stheta(:,1),stheta(:,2),'.')
    title(j{i})
    
end
