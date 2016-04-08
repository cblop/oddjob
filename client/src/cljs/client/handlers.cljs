(ns client.handlers
    (:require [re-frame.core :as re-frame]
              [ajax.core :refer [GET POST]]
              [client.db :as db]))


(def host "http://localhost:5000")

(defn drop-nth [n coll]
  (keep-indexed #(if (not= %1 n) %2) coll))

(re-frame/register-handler
 :add-trope
 (fn [db [_ id]]
   (assoc db :our-tropes (conj (vec (:our-tropes db)) {:id nil :subverted false}))))

(re-frame/register-handler
 :subvert-trope
 (fn [db [_ n]]
   (let [sub (:subverted (nth (:our-tropes db) n))]
     (assoc db :our-tropes (assoc-in (:our-tropes db) [n :subverted] (not sub))))))

(re-frame/register-handler
 :change-trope
 (fn [db [_ n id]]
   (println (:our-tropes db))
   (assoc db :our-tropes (assoc (:our-tropes db) n {:id id :subverted false}))))


(re-frame/register-handler
 :change-char
 (fn [db [_ n id]]
   (let [chars (:chars (nth (:our-tropes db) n))]
     (assoc db :our-tropes (assoc-in (:our-tropes db) [n :chars] (conj chars id))))))

(re-frame/register-handler
 :remove-trope
 (fn [db [_ n]]
   (let [a (:our-tropes db)]
         (assoc db :our-tropes (drop-nth n a)))))

(re-frame/register-handler
 :trope-selected
 (fn [db [_ n id]]
   (assoc-in db [:our-tropes n] {:id id :subverted false})))

(re-frame/register-handler
 :tab-changed
 (fn [db [_ tab-id]]
   (assoc db :current-tab tab-id)))

(re-frame/register-handler
 :load-tropes-handler
 (fn [db [_ response]]
   (assoc db :tropes (:tropes response))))


(re-frame/register-handler
 :load-chars-handler
 (fn [db [_ response]]
   (assoc db :characters (:characters response))))

(re-frame/register-handler
 :load-arches-handler
 (fn [db [_ response]]
   (assoc db :archetypes (:archetypes response))))

(defn handler [response]
  (.log js/console (str response)))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "something bad happened: " status " " status-text)))

(defn gen-handler [response]
  (do
    (re-frame/dispatch [:story-map response])
    (re-frame/dispatch [:show])))

(re-frame/register-handler
 :story-map
 (fn [db [_ smap]]
   (let [values (vals smap)
         sorted (sort-by #(first (first %)) values)
         phases (group-by #(first (first %)) values)
         sorted (sort-by key phases)
         sorts (mapcat (fn [x] (sort-by #(second (first %)) x)) (vals sorted))
         x (println "SORTS: ")
         p (println sorts)
         ;; sorted ()
         ;; story-s (map second sorted)
         story-s (map second sorts)
         story-string (map #(clojure.string/capitalize %) story-s)
         p (println smap)
         ]
     (assoc db :story story-string))))

(defn generate-story [chars]
  (POST (str host "/story")
      {:format :json
       ;; :keywords? true
       :response-format :json
       :handler gen-handler
       :error-handler error-handler
       :params {:chars (map #([% ""]) chars)}}))

(re-frame/register-handler
 :generate-story
 (fn [db [_]]
   (let [story
         (cond (some #(nil? %) (:our-tropes db)) (do (js/alert "One of your tropes is blank!") "")
               (some #(nil? %) (mapcat :chars (:our-tropes db))) (do (js/alert "One of your characters is blank!") "")
               :else (generate-story (mapcat :chars (:our-tropes db))))
         ;; p (println story-string)
         ]
     db
     ;; PUT request to server
     ;; (assoc db :story story-string)
     )))

(re-frame/register-handler
 :hide
 (fn [db _]
   (assoc db :show false)
   ))

(re-frame/register-handler
 :show
 (fn [db _]
   (assoc db :show true)
   ))

(re-frame/register-handler
 :bad-response
 (fn [db [_ response]]
   (println (str "BAD RESPONSE: " response))
   db
   ))

(re-frame/register-handler
 :load-tropes
 (fn [db _]
   (GET (str host "/tropes/") {:handler #(re-frame/dispatch [:load-tropes-handler %1])
                               :bad-response #(re-frame/dispatch [:bad-response %1])
                               :response-format :json
                               :keywords? true})
   db))


(re-frame/register-handler
 :load-characters
 (fn [db _]
   (GET (str host "/characters/") {:handler #(re-frame/dispatch [:load-chars-handler %1])
                                   :bad-response #(re-frame/dispatch [:bad-response %1])
                                   :response-format :json
                                   :keywords? true})
   db))


(re-frame/register-handler
 :load-archetypes
 (fn [db _]
   (GET (str host "/archetypes/") {:handler #(re-frame/dispatch [:load-arches-handler %1])
                                   :bad-response #(re-frame/dispatch [:bad-response %1])
                                   :response-format :json
                                   :keywords? true})
   db))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
     db/default-db))
