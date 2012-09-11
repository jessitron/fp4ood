using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.FSharp.Core;

namespace PricingEngine
{

    class Variable
    {
        private readonly string name;
        private readonly Func<int, double> calculation;

        public Variable(string name, Func<int, double> calculation)
        {
            this.calculation = calculation;
            this.name = name;
        }

        public Func<int,double> ValueAt
        {
            get {return calculation;}
        } 
    }
    class Program
    {
        static void Main(string[] args)
        {
            var cost = new Variable("cost", i => Math.Pow(0.15,i));

            Console.WriteLine(cost.ValueAt(1));

            TrySomeOtherStuff();
            Console.ReadLine();
        }

        private static void TrySomeOtherStuff()
        {
            var lines = new List<string>();
            lines.Add("blah blah blah");
            lines.Add("BUG 1"); 
            lines.Add("blah blah blah");
            lines.Add("BUG 2");

            var output = lines.Where(s => s.StartsWith("BUG")).ToList();

            foreach (var s in output)
                 Console.WriteLine(s);


            // FSharpOption
            FSharpOption<string> some = FSharpOption<string>.Some("value");

            var c = new Customer((FirstName)"Josh");

            FirstName eric = "Eric";
        }
    }

    class Customer
    {
        public Customer(FirstName first)
        {
            
        }
    }

    class FirstName
    {
        public string ToString()
        {
            return value;
        }
        private readonly string value;

        public FirstName(string value)
        {
            this.value = value;
        }

        public static implicit operator FirstName(string value)
        {
            return new FirstName(value);
        }
    }
}
