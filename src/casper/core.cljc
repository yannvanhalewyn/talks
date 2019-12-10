(ns casper.core)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Helpers

(defn- transition [slide transition]
  (assoc-in slide [:slide/section :data-transition] transition))

(defn- make-slide* [{:keys [class background-image hide-logo?]} & body]
  #:slide {:body (into [:div.text-center] body)
           :hide-logo? hide-logo?
           :section-props {:class class
                           :data-background-image background-image}})

(defn- render [{:slide/keys [body section-props hide-logo?]}]
  [:section.flex.flex-col.justify-between.h-full.text-left
   (merge {:style {:padding "10vmin"}} section-props)
   (if hide-logo?
     body
     [:<>
      [:strong "brightin"]
      body
      [:div.text-xs.text-right.italic
       "Yann Vanhalewyn"]])])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Public

(defn transition-group [[in during out] [first & others]]
  (let [middle (butlast others)
        end    (last others)]
    [(transition first (str in " " during "-out" " "))
     (map #(transition % during) middle)
     (transition end (str during "-in " out " "))]))

(defn render-slides [slides]
  (for [slide (flatten slides)]
    ^{:keys (:slide/key slide)}
    (render slide)))

#?(:clj
   (defmacro defslide [name opts & body]
     (let [opts (assoc opts :slide/key name)]
       `(def ~name ~(apply make-slide* opts body)))))
