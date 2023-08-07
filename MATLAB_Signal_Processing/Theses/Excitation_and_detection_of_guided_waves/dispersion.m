% The purpose of the script is to plot dispersion curves for the analyzed
% composite.
% 
%In this script, after loading the measurement data, one of the five sets
% is selected, i.e. lying in the direction of 0°, 45°, 90°, 135° or 180°.
% Then, after low-pass filtering matched to the signal's excitation band,
% the two-dimensional Fourier transform of the signal is calculated and
% converted to the decibel scale. In the next step, the spatial and
% frequency resolution is determined and the data is scaled accordingly.
% From the graphs generated in this way, it is possible to read the
% dispersion curves and the properties of the transmitter used, such as its
%resonance.

clear all
close all
clc

% load('excitation_signal_234827_140634')
% t1 = (0:S1.ChirpSamples - 1)*S1.dt;
% exc = S1.y(1:S1.ChirpSamples);

p2l = './MFC_ALU\piezov2\';
fn = 'chirp_50_250_5usrednien_composite_5_linii.svd';

xyz_ = 0;

load([p2l  fn '.mat'])
t = VibData.t;
fs = 1/(t(2)-t(1));
xyz = VibData.XYZ(:,1:2);

%%
% exci = interp1(t1,exc,t);
% exci(isnan(exci)) = 0;

%% select points at a given line
% 
% sel = find(((xyz(:,2)<0.002) & (xyz(:,2)>0.000701) & (xyz(:,1)>0.0001)) | ((xyz(:,1)>0.001)&(xyz(:,2)<0.00070)),100,'last');
% sel = find((xyz(:,2)>0.002) & (xyz(:,1)>0.001) & (xyz(:,1)>0.5*(xyz(:,2))),100,'last');
% sel = find((xyz(:,1)<0.6*(xyz(:,2))) & xyz(:,1)>0.0007,100,'last');
% sel = find((0.002<(xyz(:,2))) & xyz(:,1)<0.0001 & (xyz(:,1)>-3*(xyz(:,2))),100,'last');
sel = find((xyz(:,1)<-1*(xyz(:,2))) & xyz(:,1)<0.0001,100,'last');

figure(1)
plot(xyz(:,1), xyz(:,2),'.')
plot(xyz(:,1), xyz(:,2),'.', xyz(sel,1), xyz(sel,2),'s')
axis equal
xlabel('x (mm)')
ylabel('y (mm)')
set(gca, 'fontsize', 12), box off

%% perfrom low-pass filtration
data = VibData.x(sel,:);
data = lowpass(data,300e3,fs);
t = VibData.t(:);

%%
% for i=1:size(data,1)
%     
%     [R, lags]= xcorr(data(i,:),exci);
%     data1(i,:) = R((1+length(R))/2:end);
% end
% 
% figure()
% imagesc(data1)
% xlim([0 1000])
% caxis([-1 1])

%% fft2
fdata = fft2(data, 1000, 5000);
dx = abs(mean(diff(sqrt(xyz(sel,1).^2+xyz(sel,2).^2))));

kv = [0 : size(fdata,1)-1] / (dx*size(fdata,1)) ;
fv = [0 : (size(fdata,2)-1)] * fs/size(fdata,2);

figure()
pcolor(fv,kv,flipud(20*log10(abs(fdata))))
shading flat
caxis([-40 -20]+max(caxis))
xlabel('f [Hz]')
ylabel('k [1/m]')

figure()
pcolor(fv,kv,flipud(20*log10(abs(fdata))))
shading flat
caxis([-40 -20]+max(caxis))
xlim([0 300e3])
ylim([0 200])
xlabel('f [Hz]')
ylabel('k [1/m]')

