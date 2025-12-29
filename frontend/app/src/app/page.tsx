"use client";
import { useState, useEffect } from "react";
import {
  Users,
  Package,
  ShoppingCart,
  Bell,
  BarChart3,
  MessageSquare,
  Activity,
  ArrowUpRight,
  ArrowDownRight,
  CreditCard
} from "lucide-react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Progress } from "@/components/ui/progress";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Badge } from "@/components/ui/badge";

export default function Dashboard() {
  const [mounted, setMounted] = useState(false);
  const [stats, setStats] = useState({
    users: "...",
    orders: "...",
    revenue: "...",
    alerts: "..."
  });
  const [notifications, setNotifications] = useState([]);
  const [services, setServices] = useState([]);

  useEffect(() => {
    setMounted(true);
    fetchDashboardData();
    const interval = setInterval(fetchDashboardData, 10000); // Polling every 10s
    return () => clearInterval(interval);
  }, []);

  const fetchDashboardData = async () => {
    try {
      // In a real demo, we'd fetch from http://localhost:8080/api/...
      // For this step, I'll implement the structure to handle these calls
      const [usersRes, ordersRes, analyticsRes] = await Promise.all([
        fetch("http://localhost:8080/api/users/count").catch(() => null),
        fetch("http://localhost:8080/api/orders/active").catch(() => null),
        fetch("http://localhost:8080/api/analytics/summary").catch(() => null)
      ]);

      // Mocking some fallback logic if services are down during development
      setStats({
        users: usersRes ? await usersRes.json() : "12,482",
        orders: ordersRes ? await ordersRes.json() : "156",
        revenue: analyticsRes ? (await analyticsRes.json()).revenue : "$45,231.89",
        alerts: "14"
      });
    } catch (error) {
      console.error("Failed to fetch dashboard data", error);
    }
  };

  if (!mounted) return null;

  return (
    <div className="min-h-screen bg-background p-6 md:p-10">
      <div className="max-w-7xl mx-auto space-y-8">
        {/* Header */}
        <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
          <div>
            <h1 className="text-4xl font-extrabold tracking-tight text-primary">Microservices Demo</h1>
            <p className="text-muted-foreground mt-1 text-lg">Real-time ecosystem monitor for your online shop.</p>
          </div>
          <div className="flex items-center gap-3">
            <Button variant="outline" size="sm" className="glass">
              <Activity className="mr-2 h-4 w-4" /> System Healthy
            </Button>
            <Button size="sm" className="bg-primary text-primary-foreground shadow-lg hover:shadow-primary/20">
              Refresh Data
            </Button>
          </div>
        </div>

        {/* Stats Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          <StatCard
            title="Total Users"
            value="12,482"
            change="+12% from last month"
            icon={<Users className="h-5 w-5" />}
            trend="up"
          />
          <StatCard
            title="Active Orders"
            value="156"
            change="+5% since last hour"
            icon={<ShoppingCart className="h-5 w-5" />}
            trend="up"
          />
          <StatCard
            title="Revenue"
            value="$45,231.89"
            change="-2% from yesterday"
            icon={<CreditCard className="h-5 w-5" />}
            trend="down"
          />
          <StatCard
            title="Inventory Alerts"
            value="14"
            change="3 critical stock items"
            icon={<Package className="h-5 w-5" />}
            trend="down"
            critical
          />
        </div>

        {/* Main Content */}
        <Tabs defaultValue="overview" className="space-y-6">
          <TabsList className="glass p-1">
            <TabsTrigger value="overview">Overview</TabsTrigger>
            <TabsTrigger value="services">Services</TabsTrigger>
            <TabsTrigger value="events">Events Timeline</TabsTrigger>
          </TabsList>

          <TabsContent value="overview" className="space-y-6">
            <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
              {/* Sales Chart Mockup */}
              <Card className="lg:col-span-2 shadow-xl border-none glass overflow-hidden">
                <CardHeader>
                  <CardTitle>Global Sales Activity</CardTitle>
                  <CardDescription>Real-time analytics across all regions.</CardDescription>
                </CardHeader>
                <CardContent className="h-[300px] flex items-end justify-between gap-2 p-6 pt-0">
                  {[40, 70, 45, 90, 65, 80, 50, 85, 95, 75, 60, 100].map((h, i) => (
                    <div key={i} className="w-full bg-primary/20 rounded-t-sm transition-all duration-500 hover:bg-primary" style={{ height: `${h}%` }} />
                  ))}
                </CardContent>
              </Card>

              {/* Recent Notifications */}
              <Card className="shadow-xl border-none glass">
                <CardHeader>
                  <CardTitle className="flex items-center">
                    <Bell className="mr-2 h-5 w-5 text-primary" /> Recent Alerts
                  </CardTitle>
                </CardHeader>
                <CardContent className="space-y-4">
                  <NotificationItem
                    title="New User Registered"
                    time="2 mins ago"
                    description="user_992 registered via Web Portal"
                  />
                  <NotificationItem
                    title="Order #9422 Placed"
                    time="5 mins ago"
                    description="Payment verified for $299.00"
                  />
                  <NotificationItem
                    title="Low Stock Warning"
                    time="12 mins ago"
                    description="Product SKU: MB-PRO-14 is below threshold"
                    urgent
                  />
                </CardContent>
              </Card>
            </div>
          </TabsContent>

          <TabsContent value="services" className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            <ServiceCard name="User Service" type="Postgres" status="Up" events="24/m" />
            <ServiceCard name="Order Service" type="Postgres + RabbitMQ" status="Up" events="18/m" />
            <ServiceCard name="Inventory Service" type="Postgres + RabbitMQ" status="Up" events="12/m" />
            <ServiceCard name="Product Service" type="MongoDB" status="Up" events="5/m" />
            <ServiceCard name="Analytics Service" type="MongoDB + RabbitMQ" status="Up" events="48/m" />
            <ServiceCard name="Notification Service" type="RabbitMQ" status="Up" events="15/m" />
          </TabsContent>
        </Tabs>
      </div>
    </div>
  );
}

function StatCard({ title, value, change, icon, trend, critical = false }) {
  return (
    <Card className={`border-none shadow-lg glass overflow-hidden group hover:scale-[1.02] transition-all duration-300`}>
      <CardHeader className="flex flex-row items-center justify-between pb-2">
        <CardTitle className="text-sm font-medium text-muted-foreground">{title}</CardTitle>
        <div className={`p-2 rounded-lg ${critical ? 'bg-destructive/10 text-destructive' : 'bg-primary/10 text-primary'} group-hover:scale-110 transition-transform`}>
          {icon}
        </div>
      </CardHeader>
      <CardContent>
        <div className="text-2xl font-bold">{value}</div>
        <p className={`text-xs flex items-center mt-1 ${trend === 'up' ? 'text-emerald-500' : 'text-rose-500'}`}>
          {trend === 'up' ? <ArrowUpRight className="h-3 w-3 mr-1" /> : <ArrowDownRight className="h-3 w-3 mr-1" />}
          {change}
        </p>
      </CardContent>
    </Card>
  );
}

function NotificationItem({ title, time, description, urgent = false }) {
  return (
    <div className="flex gap-3 p-2 rounded-lg hover:bg-primary/5 transition-colors cursor-pointer border-l-2 border-transparent hover:border-primary">
      <div className={`mt-1 h-2 w-2 rounded-full flex-shrink-0 ${urgent ? 'bg-rose-500' : 'bg-emerald-500'}`} />
      <div className="space-y-1">
        <div className="flex items-center justify-between gap-4">
          <p className="text-sm font-medium leading-none">{title}</p>
          <span className="text-[10px] text-muted-foreground uppercase">{time}</span>
        </div>
        <p className="text-xs text-muted-foreground line-clamp-1">{description}</p>
      </div>
    </div>
  );
}

function ServiceCard({ name, type, status, events }) {
  return (
    <Card className="border-none shadow-md glass group overflow-hidden">
      <CardHeader className="pb-2">
        <div className="flex items-center justify-between">
          <CardTitle className="text-base font-bold">{name}</CardTitle>
          <Badge variant="outline" className="bg-emerald-500/10 text-emerald-500 border-emerald-500/20">
            {status}
          </Badge>
        </div>
        <CardDescription>{type}</CardDescription>
      </CardHeader>
      <CardContent>
        <div className="flex items-center justify-between text-sm">
          <span className="text-muted-foreground">Throughput</span>
          <span className="font-mono font-medium">{events}</span>
        </div>
        <Progress value={Math.random() * 40 + 60} className="h-1 mt-3" />
      </CardContent>
    </Card>
  );
}