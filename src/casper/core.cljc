(ns casper.core)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Helpers

(defn- transition [slide transition]
  (assoc-in slide [:slide/section :data-transition] transition))

(defn- make-slide* [{:keys [class]} & body]
  #:slide {:body (into [:div.text-center] body)
           :section {:style "padding:10vmin"
                     :class class}})

(defn- render [{:slide/keys [body section hide-logo?]}]
  [:section
   (update section
     :class str " flex flex-col justify-between h-full text-left")
   (when-not hide-logo?
     [:strong "brightin"])
   body
   (when-not hide-logo?
     [:div.text-xs.text-right.italic
      "Yann Vanhalewyn"])])

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
     `(def ~name ~(apply make-slide* opts body))))
