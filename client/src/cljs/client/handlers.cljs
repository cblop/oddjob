(ns client.handlers
    (:require [re-frame.core :as re-frame]
              [client.db :as db]))

(re-frame/register-handler
 :tab-changed
 (fn [db [_ tab-id]]
   (assoc db :current-tab tab-id)))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/default-db))
