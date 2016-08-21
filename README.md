<h1>Super Simple Stocks Requirements</h1>
<p>a.  For a given stock:</p>
<ol>
<li><p>Provide working source code that will:</p>
<pre><code>i.    Calculate the dividend yield.
ii.   Calculate the P/E Ratio.
iii.  Record a trade, with timestamp, quantity of shares, buy or sell indicator and price.
iv.   Calculate Stock Price based on trades recorded in past 15 minutes.
</code></pre>

<p>b.  Calculate the GBCE All Share Index using the geometric mean of prices for all stocks</p></li>
</ol>

<h5>
For formula and other specification see doc\Super Simple Stocks.docx
</h5>

<h1>Solution</h1>

We provide a set of objects that model the required enviroments.
A service interface for the required operations that enable interacting with market add stock, trade its and calculate the required formula.

All example is based on an in memory persistent structure that is modelled by the class PersistenceMarket.

For example we write a test class based on JUnit3 for the StockFactory object and a simple simulation test for all the operation required.