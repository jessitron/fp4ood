// Learn more about F# at http://fsharp.net
// See the 'F# Tutorial' project for more help.

let something(r : System.Random) =
   match r.Next(3) with
    | 0 -> "armadillo"
    | 1 -> "banana"
    | 2 -> "BUG time stuff"

open System.IO
let saySomething(out : StreamWriter, r : System.Random) =
  out.WriteLine(something(r))
  out.Flush()

open System.Threading
let rec keepSayingShitForever(out : StreamWriter, r : System.Random) =
  Thread.Sleep(r.Next(5000))
  saySomething(out, r)
  keepSayingShitForever(out, r)
     
[<EntryPoint>]
let main argv = 
    printfn "%A" argv
    let out = new StreamWriter(File.Open("logFile.txt",  FileMode.OpenOrCreate, FileAccess.Write, FileShare.Read))
    keepSayingShitForever(out, new System.Random())
    0 // return an integer exit code
