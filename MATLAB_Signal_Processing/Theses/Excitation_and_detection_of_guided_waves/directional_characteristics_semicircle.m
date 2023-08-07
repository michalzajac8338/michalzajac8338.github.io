% Processing the results using this script is to plot the directional
% characteristics of subsequent transducers.
% 
% The script has a function like 'directional_characteristics.m' The only
% difference is that there is no need to select points so that they are at
% a constant distance from the transducer, because this fact was taken
% into account at the measurement stage.

clear all
close all
clc

%%
p2l = './MFC_ALU\2814v2\';
FN = {'80kHz_5cyc_composite_polkole_20usr.svd'; '100kHz_5cyc_composite_polkole_20usr.svd'; '150kHz_5cyc_composite_polkole_20usr.svd'; '200kHz_5cyc_composite_polkole_20usr.svd'};
LOWPASS_FILTER = [100e3 130e3 180e3 235e3];
j = {'80kHz' , '100kHz' ,'150kHz' ,'200kHz' };

xyz_ = 0.03; %dla polokr 2814
% 0.01
for i = 1:4
    % fn = FN(i,:);
    fn = FN{i};
    
    LOWPASS_FILTER = LOWPASS_FILTER(i);
    load([p2l  fn '.mat'])
    t = VibData.t;
    fs = 1/(t(2)-t(1));
    %%
    xyz = VibData.XYZ(:,1:2);
    
    theta = -30 *pi/180;
    R = [cosd(theta) -sind(theta); sind(theta) cosd(theta)];
    
    xyz = xyz * R;
    
    xyz(:,2) =  xyz(:,2)-xyz_;
    
    %% plot points
    
%     figure(1)
%     plot(xyz(:,1), xyz(:,2),'.')
%     axis equal
%     xlabel('x [m]')
%     ylabel('y [m]')
%     set(gca, 'fontsize', 12), box off
    %%
    
    theta = atan2(xyz(:,2),xyz(:,1));
    
    % perfrom low-pass filtration
    data = VibData.x';
    data = lowpass(data,LOWPASS_FILTER,fs);
    t = VibData.t;
    
    % figure()
    % plot(t, data(:,[1 end]), t, abs(hilbert(data(:,[1 end]))))
    maxVal = max(abs(hilbert(data)));
%     maxVal(maxVal>0.004) = 0;
    
    %% sorting and median
    
    stheta = [theta, maxVal'];
    stheta = sortrows(stheta,1);
    stheta(:,2) = medfilt1(stheta(:,2),5);
    
    figure(i+1)
    polar(stheta(:,1),stheta(:,2),'.')
    set(gca,'fontsize',12)
    title(j{i});
    
end