using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.IO;

namespace LogReaderDeclarative
{
    class Program
    {
        static void Main(string[] args)
        {

            Console.WriteLine("go!");

            using (FileStream fileStream = File.Open(@"C:\Users\jessitron\AppData\Local\Temp\you", FileMode.Open, FileAccess.Read, FileShare.ReadWrite))
            using (var file = new StreamReader(fileStream))
            {
                var lines = ReadLines(file)
                                .Where(line => line.StartsWith("BUG"))
                                .Select(FormatBugLine)
                                .Take(40);
                foreach (var line in lines)
                {
                    Report(line);
                }

                Console.WriteLine("yaaaay");
            }
        }

        static string FormatBugLine(string line)
        {
            string[] words = line.Split(' ');
            return "Saw the bug at " + words[1] + " on " + words[2];
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

        static IEnumerable<string> ReadLines(StreamReader reader)
        {
            while (true)
            {
                WaitUntilFileHasMoreData(reader);
                yield return reader.ReadLine();
            }
        }
    }
}
