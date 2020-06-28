# import os
# import warnings
# warnings.filterwarnings('ignore')
# import pandas as pd
# import numpy as np
# import matplotlib.pyplot as plt
# plt.style.use('fivethirtyeight')
# from pylab import rcParams
# rcParams['figure.figsize'] = 10, 6
# from datetime import datetime
# from statsmodels.tsa.stattools import adfuller
# from statsmodels.tsa.seasonal import seasonal_decompose
# from statsmodels.tsa.arima_model import ARIMA
# from pmdarima.arima import auto_arima
# from sklearn.metrics import mean_squared_error, mean_absolute_error
# import math
# from math import sqrt
#
# df = pd.read_csv(r'TD.csv')
#
# con = df['Date']
#
# df['Date'] = pd.to_datetime(df['Date'])
#
# df.set_index('Date', inplace = True)
#
# df['year'] = df.index.year
# df['month'] = df.index.month
# df['day'] = df.index.day
#
# test = df[629:]
# train = df[:628]
#
# def test_stationarity(timeseries):
#
#     #Determing rolling statistics
#     rolmean = timeseries.rolling(12).mean()
#     rolstd = timeseries.rolling(12).std()
#     #Plot rolling statistics:
#     plt.plot(timeseries, color='blue',label='Original')
#     plt.plot(rolmean, color='red', label='Rolling Mean')
#     plt.plot(rolstd, color='black', label = 'Rolling Std')
#     plt.legend(loc='best')
#     plt.title('Rolling Mean and Standard Deviation')
#     plt.show(block=False)
#
#     print("Results of dickey fuller test")
#     adft = adfuller(timeseries,autolag='AIC')
#     # output for dft will give us without defining what the values are.
#     #hence we manually write what values does it explains using a for loop
#     output = pd.Series(adft[0:4],index=['Test Statistics','p-value','No. of lags used','Number of observations used'])
#     for key,values in adft[4].items():
#         output['critical value (%s)'%key] = values
#         print(output)
#
#
# train_log = np.log(train['Close'])
# test_log = np.log(test['Close'])
# moving_avg = train_log.rolling(24).mean()
#
# train_log_moving_avg_diff = train_log - moving_avg
#
# train_log_moving_avg_diff.dropna(inplace = True), test_stationarity(train_log_moving_avg_diff)
#
# train_log_diff = train_log - train_log.shift(1)
# test_stationarity(train_log_diff.dropna())
#
# model = auto_arima(train_log, trace=True, error_action='ignore', suppress_warnings=True)
# model.fit(train_log)
# forecast = model.predict(n_periods=len(test))
# forecast = pd.DataFrame(forecast,index = test_log.index,columns=['Prediction'])
# #plot the predictions for validation set
# plt.plot(train_log, label='Train')
# plt.plot(test_log, label='Test')
# plt.plot(forecast, label='Prediction')
# plt.title('TD Stock Price Prediction')
# plt.xlabel('Time')
# plt.ylabel('Actual Stock Price')
# plt.legend(loc='upper left', fontsize=8)
# plt.show()
#
# rms = sqrt(mean_squared_error(test_log,forecast))
#
# print("RMSE: ", rms)
#
# # print(df.index)

import stocker

print(stocker.predict.tomorrow('NDAQ'))
