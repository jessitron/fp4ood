using System;
using System.Collections.Generic;
using System.Threading;
using System.Text;
using System.Threading.Tasks;
using System.IO;

namespace LogReaderImperative
{
    class Program
    {
        static void Main(string[] args)
        {
            using (FileStream fileStream = File.Open(@"C:\Users\jessitron\AppData\Local\Temp\you", FileMode.Open, FileAccess.Read, FileShare.ReadWrite))
            using ( var file = new StreamReader(fileStream)) {

            Console.WriteLine("yaaaay");

            int bugCount = 0;
            string nextLine = file.ReadLine();
            while (bugCount < 40)
            {
                if (nextLine.StartsWith("BUG"))
                {
                    string[] words = nextLine.Split(' ');
                    Report("Saw the bug at " + words[1] + " on " + words[2]);
                    bugCount++;
                }
                WaitUntilFileHasMoreData(file);
                nextLine = file.ReadLine();
            }


            }
        }

        static void Report(string problem)
        {
            // I don't care what this does.
            Console.WriteLine(problem);
        }

        static void WaitUntilFileHasMoreData(StreamReader file)
        {
            while (file.EndOfStream)
            {
                Thread.Sleep(5);
            }
        }
    }
}
