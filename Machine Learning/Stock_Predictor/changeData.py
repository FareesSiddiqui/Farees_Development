import numpy as np
import matplotlib.pyplot as plt
from sklearn.preprocessing import MinMaxScaler
import pandas as pd

HOU_training_complete = pd.read_csv(r'C:\Users\faree\Documents\Github\Farees_Development\Machine Learning\Stock_Predictor\Dataset\hou_train.csv')
HOU_training_processed = HOU_training_complete.iloc[:, 1:2].values

scaler = MinMaxScaler(feature_range = (0, 1))
hou_training_scaled = scaler.fit_transform(HOU_training_processed)

features_set = []
labels = []

for i in range(60, 3106):
    features_set.append(hou_training_scaled[i-60:i, 0])
    labels.append(hou_training_scaled[i, 0])

features_set, labels = np.array(features_set), np.array(labels)

features_set = np.reshape(features_set, (features_set.shape[0], features_set.shape[1], 1))
