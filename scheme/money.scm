;; Figure out how much money you will have given:
;; years: number of years
;; intr: interest rate in decimal
;; n: initial savings
;; m: savings per year
;; Note: This assumes the interest rate is static, that you'll always save the 
;; same amount every year, and obviously that you're only using fixed interest
;; savings accounts as the sole method of accumulating money.
(define (compound years intr n m)
  ; Just the interest from a given amount for one year.
  (define (interest rate amt)
    (* rate amt))
  ; Total of the starting amt, the money from interest, and the money saved
  ; that year.
  (define (year-total initial-amt rate saved-year-amt)
    (+ initial-amt (interest rate initial-amt) saved-year-amt))
  ; Recurse through the number of years.
  (cond ((= years 0) n)
        (else (year-total (compound (- years 1) intr n m) intr m))))

;; Figure out how much money you will have given:
;; years: number of years
;; intr: interest rate in decimal
;; n: initial savings
;; m: savings per year
;; i: average inflation rate
(define (compound-inflate years intr n m i)
  ; Calculate year modifier.  Used for interest or inflation.
  (define (year-modifier rate amt)
    (* rate amt))
  ; Total of the starting amt, the money from interest, and the money saved
  ; that year, less inflation.
  (define (year-total initial-amt rate saved-year-amt inflation-rate)
    (+ initial-amt
       (year-modifier rate initial-amt)
       saved-year-amt
       (- 0 (year-modifier inflation-rate initial-amt))))
  ; Recurse through the number of years.
  (cond ((= years 0) n)
        (else (year-total (compound-inflate (- years 1) intr n m i)
                          intr m i))))

;; Figure out how much money you will have given:
;; years: number of years
;; intr: interest rate in decimal
;; n: initial savings
;; m: savings per year
;; i: average inflation rate
;; t: capital gain tax rate
(define (compound-inflate-tax years intr n m i t)
  ; Calculate year modifier.  Used for interest, inflation, or tax.
  (define (year-modifier rate amt)
    (* rate amt))
  ; Total of the starting amt, the money from interest, and the money saved
  ; that year, less inflation and taxes.
  (define (year-total initial-amt rate saved-year-amt inflation tax)
    (+ initial-amt
       (year-modifier rate initial-amt)
       saved-year-amt
       (- 0 (year-modifier inflation initial-amt))
       (- 0 (year-modifier tax (year-modifier rate initial-amt)))))
  ; Recurse through the number of years.
  (cond ((= years 0) n)
        (else (+ (year-total (compound-inflate-tax (- years 1)
                                                   intr n m i t)
                             intr m i t)))))


;; Convert an amount of future dollars into today dollars.
(define (de-inflate years amt inflation-rate)
  (cond ((= years 0) amt)
        (else (de-inflate (- years 1)
                          (- amt (* amt inflation-rate)) inflation-rate))))

;; Model the spin of a roulette wheel (1 - 36, 37 = 00, 0 = 0)
;; TODO: Finish this.
;(define (roulette-spin)
;  (require 'random)
;  (random 38))
