(ns client.handlers
    (:require [re-frame.core :as re-frame]
              [client.db :as db]))

(re-frame/register-handler
 :add-trope
 (fn [db [_ id]]
     (assoc db :our-tropes (conj (:our-tropes db) nil))))

(re-frame/register-handler
 :trope-selected
 (fn [db [_ n id]]
   (assoc-in db [:our-tropes n] id)))

(re-frame/register-handler
 :tab-changed
 (fn [db [_ tab-id]]
   (assoc db :current-tab tab-id)))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/default-db))
